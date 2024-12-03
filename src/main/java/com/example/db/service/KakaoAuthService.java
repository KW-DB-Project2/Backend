package com.example.db.service;

import com.example.db.dto.KakaoAccountDTO;
import com.example.db.dto.KakaoTokenDTO;
import com.example.db.dto.LoginResponseDTO;
import com.example.db.entity.Account;
import com.example.db.dto.ExtraInfoRequest;
import com.example.db.entity.JwtResponse;
import com.example.db.entity.RefreshToken;
import com.example.db.enums.UserRole;
import com.example.db.jdbc.MemberRepository;
import com.example.db.jdbc.RefreshTokenRepository;
import com.example.db.jwt.JwtUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class KakaoAuthService {

    @Value("${kakao.client-id}")
    private String CLIENT_ID;
    @Value("${kakao.redirect-uri}")
    private String REDIRECT_URI;
    @Value("${kakao.token-uri}")
    private String KAKAO_TOKEN_URI;
    @Value("#{'${admin.emails}'.split(',')}")
    private List<String> adminEmails;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public String getKakaoAccessToken(String code) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", CLIENT_ID);
        params.add("redirect_uri", REDIRECT_URI);
        params.add("code", code);

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);

        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> accessTokenResponse = rt.exchange(
                KAKAO_TOKEN_URI,
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        KakaoTokenDTO kakaoTokenDto = objectMapper.readValue(accessTokenResponse.getBody(), KakaoTokenDTO.class);

        return kakaoTokenDto.getAccess_token();
    }

    @Transactional
    public ResponseEntity<LoginResponseDTO> kakaoLogin(String kakaoAccessToken) {

        HttpHeaders headers = new HttpHeaders();
        LoginResponseDTO loginResponseDto = new LoginResponseDTO();
        Account account;

        try {
            // 카카오 사용자 정보 가져오기
            account = getKakaoInfo(kakaoAccessToken);
            if (account == null) {
                throw new IllegalArgumentException("Failed to fetch account information from Kakao");
            }

            // BAN 역할 확인
            if (account.getRole() == UserRole.BAN) {
                throw new IllegalArgumentException("This account is banned and cannot log in.");
            }

            if(adminEmails.contains(account.getEmail())){
                account.setRole(UserRole.ADMIN);
            }else {
                account.setRole(UserRole.USER);
            }


        } catch (Exception e) {
            loginResponseDto.setLoginSuccess(false);
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(loginResponseDto);
        }

        try {
            // 이미 존재하는 사용자 확인
            Account existOwner = memberRepository.findByKakaoId(account.getLoginId()).orElse(null);
            boolean isNewUser = false;
            if (existOwner == null) {
                // 사용자 없으면 새로 저장
                isNewUser = true;
                memberRepository.save(account);
            } else {
                // BAN 역할 확인
                if (existOwner.getRole() == UserRole.BAN) {
                    throw new IllegalArgumentException("This account is banned and cannot log in.");
                }

                // 사용자 있으면 기존 사용자 정보 사용
                if(!existOwner.getRole().equals(account.getRole())){
                    existOwner.setRole(account.getRole());
                    memberRepository.updateRole(existOwner.getId(), account.getRole());
                }
                account = existOwner;
                if(existOwner.getPhoneNumber() == null || existOwner.getPhoneNumber().isEmpty()){
                    isNewUser = true;
                }
            }

            // 토큰 생성
            String jwt = jwtUtil.generateToken(account.getId(), account.getEmail(), account.getUsername());
            String refreshJwt = jwtUtil.generateRefreshToken(account.getId());

            // 기존에 존재하는 refreshToken이 있으면 업데이트, 없으면 새로 저장
            RefreshToken existingToken = refreshTokenRepository.findByLoginId(account.getId()).orElse(null);

            if (existingToken != null) {
                // 기존 토큰이 있으면 업데이트
                existingToken.setToken(refreshJwt);
                existingToken.setExpiryDate(new Date(System.currentTimeMillis() + jwtUtil.getRefreshExpirationInMs()));
                refreshTokenRepository.saveOrUpdate(existingToken);  // save 메서드로 업데이트합니다.
            } else {
                // 기존 토큰이 없으면 새로 저장
                saveRefreshToken(account.getId(), refreshJwt);
            }

            headers.add("Authorization", "Bearer " + jwt);

            loginResponseDto.setLoginSuccess(true);
            loginResponseDto.setNewUser(isNewUser);
            loginResponseDto.setAccount(account);
            loginResponseDto.setJwtToken(jwt);
            loginResponseDto.setRefreshToken(refreshJwt);

            // 응답에 Authorization 헤더를 포함
            return ResponseEntity.ok().headers(headers).body(loginResponseDto);
        } catch (Exception e) {
            loginResponseDto.setLoginSuccess(false);
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(loginResponseDto);
        }
    }

    @Transactional
    public void updateKakaoExtraInfo(ExtraInfoRequest extraInfoRequest) {
        try {
            memberRepository.updatePhoneNumber(extraInfoRequest.getId(), extraInfoRequest.getPhoneNumber());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to update Kakao Extra Info");
        }
    }


    private void saveRefreshToken(Long loginId, String refreshToken) {
            RefreshToken token = new RefreshToken();
            token.setLoginId(loginId);
            token.setToken(refreshToken);
            token.setExpiryDate(new Date(System.currentTimeMillis() + jwtUtil.getRefreshExpirationInMs()));
            refreshTokenRepository.saveOrUpdate(token);
        }

    /**
     *여기 부분 수정 userId가 아니라 KakaoId로 저장하도록
     */
    public ResponseEntity<?> refreshToken(String refreshToken) {
            if (jwtUtil.validateToken(refreshToken) && !jwtUtil.isTokenExpired(refreshToken)) {
                //Long userId = jwtUtil.getUserIdFromToken(refreshToken);
                //Long userId = jwtUtil.getLoginIdFromToken(refreshToken);
                Long idFromToken = jwtUtil.getIdFromToken(refreshToken);
                String email = jwtUtil.getEmailFromToken(refreshToken);
                String username = jwtUtil.getUsernameFromToken(refreshToken);
                //String newJwt = jwtUtil.generateToken(userId, email, username);
                String newJwt = jwtUtil.generateToken(idFromToken, email, username);
                return ResponseEntity.ok(new JwtResponse(newJwt, refreshToken));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired refresh token");
            }
        }

        public Account getKakaoInfo(String kakaoAccessToken) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + kakaoAccessToken);
            headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

            HttpEntity<MultiValueMap<String, String>> accountInfoRequest = new HttpEntity<>(headers);

            RestTemplate rt = new RestTemplate();
            ResponseEntity<String> accountInfoResponse = rt.exchange(
                    "https://kapi.kakao.com/v2/user/me",
                    HttpMethod.POST,
                    accountInfoRequest,
                    String.class
            );

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            KakaoAccountDTO kakaoAccountDto = null;
            try {
                kakaoAccountDto = objectMapper.readValue(accountInfoResponse.getBody(), KakaoAccountDTO.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            /*
            return Account.builder()
                    .kakaoId(kakaoAccountDto.getId())  // Kakao ID를 kakaoId 필드에 저장
                    .email(kakaoAccountDto.getKakao_account().getEmail())
                    .username(kakaoAccountDto.getProperties().getNickname())
                    .build();
                    */
            return new Account(
                    kakaoAccountDto.getId(),
                    kakaoAccountDto.getProperties().getNickname(),
                    kakaoAccountDto.getKakao_account().getEmail(),
                    UserRole.USER
            );
        }

        @Transactional
        public void logout(Long loginId) {
            refreshTokenRepository.deleteByUserId(loginId);
        }

        public JwtUtil getJwtUtil() {
            return jwtUtil;
        }
}
