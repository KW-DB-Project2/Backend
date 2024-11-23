package com.example.db.service;

import com.example.db.dto.LoginResponseDTO;
import com.example.db.entity.Account;
import com.example.db.entity.RefreshToken;
import com.example.db.enums.UserRole;
import com.example.db.jdbc.MemberRepository;
import com.example.db.jdbc.RefreshTokenRepository;
import com.example.db.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class LocalAuthService {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;


    public Account register(String localId, String rawPassword, String username, String email, String phoneNumber){
        Long loginId = memberRepository.generateRandomLoginId(8);
        String encodePassword = passwordEncoder.encode(rawPassword);

        Account account = new Account(loginId, localId, encodePassword, username, email,phoneNumber, UserRole.USER);
        return memberRepository.save(account);
    }

    public LoginResponseDTO login(String localId, String rawPassword){

        Account account = memberRepository.findByLocalId(localId)
                .filter(acc -> passwordEncoder.matches(rawPassword, acc.getPassword()))
                .orElseThrow(() -> new IllegalArgumentException("Invalid id or password"));

        String jwtToken = jwtUtil.generateToken(account.getId(), account.getEmail(), account.getUsername());
        String refreshToken = jwtUtil.generateRefreshToken(account.getId());

        RefreshToken existingToken = refreshTokenRepository.findByLoginId(account.getId()).orElse(null);
        if(existingToken != null){
            existingToken.setToken(refreshToken);
            existingToken.setExpiryDate(new Date(System.currentTimeMillis() + jwtUtil.getRefreshExpirationInMs()));
            refreshTokenRepository.saveOrUpdate(existingToken);
        }else{
            saveRefreshToken(account.getId(), refreshToken);
        }

        //LoginResponseDTO 생성
        LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
        loginResponseDTO.setLoginSuccess(true);
        loginResponseDTO.setAccount(account);
        loginResponseDTO.setJwtToken(jwtToken);
        loginResponseDTO.setRefreshToken(refreshToken);


        return loginResponseDTO;

    }

    private void saveRefreshToken(Long id, String refreshToken){
        RefreshToken token = new RefreshToken();
        token.setLoginId(id);
        token.setToken(refreshToken);
        token.setExpiryDate(new Date(System.currentTimeMillis() + jwtUtil.getRefreshExpirationInMs()));
        refreshTokenRepository.saveOrUpdate(token);
    }




}
