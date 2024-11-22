package com.example.db.controller;

import com.example.db.dto.LoginRequestDTO;
import com.example.db.dto.LoginResponseDTO;
import com.example.db.dto.UserDTO;
import com.example.db.entity.Account;
import com.example.db.dto.ExtraInfoRequest;
import com.example.db.entity.RefreshTokenRequest;
import com.example.db.error.CustomExceptions;
import com.example.db.error.ErrorCode;
import com.example.db.jdbc.MemberRepository;
import com.example.db.service.KakaoAuthService;
import com.example.db.service.LocalAuthService;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Objects;
import java.util.Optional;

@RestController
public class LoginController {


    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private KakaoAuthService authService;

    @Autowired
    private LocalAuthService localAuthService;


    @GetMapping("/login/oauth2/code/kakao")
    public ResponseEntity<LoginResponseDTO> login(@RequestParam("code") String code, HttpServletResponse response) {
        try {
            String kakaoAccessToken = authService.getKakaoAccessToken(code);
            System.out.println(kakaoAccessToken);
            ResponseEntity<LoginResponseDTO> loginResponse = authService.kakaoLogin(kakaoAccessToken);

            if (Objects.requireNonNull(loginResponse.getBody()).isLoginSuccess()) {
                Account account = loginResponse.getBody().getAccount();

                // 헤더에 JWT 토큰 설정
                String jwtToken = loginResponse.getHeaders().getFirst("Authorization");
                response.setHeader("Authorization", jwtToken);

                /*
                if(account.getPhoneNumber() == null || account.getPhoneNumber().isEmpty()){
                    return ResponseEntity.status(HttpStatus.FOUND)
                            .location(URI.create("/user/extra-info"))
                            .body(loginResponse.getBody());
                }
                */

                return loginResponse;
            } else {
                throw new CustomExceptions.UnauthorizedException("Unauthorized Error", null, "Unauthorized Error", ErrorCode.UNAUTHORIZED);
            }
        } catch (Exception e) {
            //e.printStackTrace();
            // 원래 에러 메시지를 그대로 반환
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new LoginResponseDTO());
        }
    }


    @PostMapping("/kakao/user/extra-info")
    public ResponseEntity<?> updateExtraInfo(@RequestBody ExtraInfoRequest extraInfoReqiest){
        try{
            authService.updateKakaoExtraInfo(extraInfoReqiest);
            return ResponseEntity.ok("User information updated successfully");
        } catch (Exception e){
            throw new CustomExceptions.InternalServerErrorException("Error message : " + e.getMessage(), e, "Error message : " + e.getMessage(), ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Account accountRequest){
        try{
            localAuthService.register(accountRequest.getLocalId(),
                    accountRequest.getPassword(),
                    accountRequest.getUsername(),
                    accountRequest.getEmail(),
                    accountRequest.getPhoneNumber());
            return ResponseEntity.ok("User registered successfully");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("failed to register user");
        }
    }

    /*
    @PostMapping("/local/login")
    public ResponseEntity<?> localLogin(@RequestParam String localId, @RequestParam String password){
        try{
            ResponseEntity<LoginResponseDTO> logined = localAuthService.login(localId, password);
            return ResponseEntity.ok("Login success");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("failed to login");
        }
    }
    */
    @PostMapping("/local/login")
    public ResponseEntity<?> localLogin(@RequestBody LoginRequestDTO loginRequest, HttpServletResponse response) {
        try {
            // 요청 데이터 출력 (디버깅)
            System.out.println("Received login request: " + loginRequest);

            // 서비스 호출
            LoginResponseDTO loginResponse = localAuthService.login(loginRequest.getLocalId(), loginRequest.getPassword());

            // JWT 토큰 설정
            String jwtToken = loginResponse.getJwtToken();
            response.setHeader("Authorization", "Bearer " + jwtToken);

            return ResponseEntity.ok(loginResponse);
        } catch (IllegalArgumentException e) {
            System.err.println("Login failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Wrong input");
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("wrong input");
        }
    }



    /*
    @PostMapping("/local/login")
    public ResponseEntity<LoginResponseDTO> localLogin(@RequestParam(name = "localId") String localId,
                                                       @RequestParam(name = "password") String password, HttpServletResponse response) {
        try {
            // LocalAuthService의 login 메서드 호출
            LoginResponseDTO loginResponse = localAuthService.login(localId, password);

            // JWT 토큰을 응답 헤더에 설정
            String jwtToken = loginResponse.getJwtToken();
            response.setHeader("Authorization", "Bearer " + jwtToken);



            // 로그인 성공 시 LoginResponseDTO 반환
            return ResponseEntity.ok(loginResponse);
        } catch (Exception e) {
            e.printStackTrace();
            // 로그인 실패 시 에러 메시지와 함께 반환
            LoginResponseDTO errorResponse = new LoginResponseDTO();
            errorResponse.setLoginSuccess(false);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
    */
    /*
            // Account 객체를 가져와 전화번호 유무 확인
            Account account = loginResponse.getAccount();


            if (account.getPhoneNumber() == null || account.getPhoneNumber().isEmpty()) {
                // 전화번호가 없는 경우 추가 정보 입력 페이지로 리다이렉트
                return ResponseEntity.status(HttpStatus.FOUND)
                        .location(URI.create("/user/extra-info"))
                        .body(loginResponse);
            }
            */



    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        try {
            return authService.refreshToken(refreshTokenRequest.getRefreshToken());
        } catch (Exception e) {
            //e.printStackTrace();
            throw new CustomExceptions.InternalServerErrorException("Error message : " + e.getMessage(), e, "Error message : " + e.getMessage(), ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }



    /*
    @GetMapping("/api/user-info")
    public ResponseEntity<?> getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }

        String username = authentication.getName();  // SecurityContext에서 사용자 이름을 가져옴

        // 사용자 정보를 조회하고 반환
        Optional<Account> accountOptional = accountRepository.findByUsername(username);

        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();
            UserDTO userDTO = new UserDTO(account.getLoginId(), account.getUsername(), account.getEmail(), account.getPhoneNumber(), account.getRole().name());
            return ResponseEntity.ok(userDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }
    */

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String token) {
        try {
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }
            //Long userId = authService.getJwtUtil().getUserIdFromToken(token);
            Long userId = authService.getJwtUtil().getLoginIdFromToken(token);
            authService.logout(userId);
            return ResponseEntity.ok().body("Successfully logged out");
        } catch (Exception e) {
            //e.printStackTrace();
            throw new CustomExceptions.InternalServerErrorException("Error message : " + e.getMessage(), e, "Error message : " + e.getMessage(), ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }


}
