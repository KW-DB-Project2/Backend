package com.example.db.service;

import com.example.db.dto.UserDTO;
import com.example.db.entity.Account;
import com.example.db.jdbc.MemberRepository;
import com.example.db.jwt.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class MypageService {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private MemberRepository memberRepository;

    // 유저 정보 조회
    public Optional<UserDTO> getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.empty();
        }

        Long loginId;
        try {
            loginId = (Long) authentication.getPrincipal(); // Principal에서 loginId 추출
            Optional<Account> optionalAccount = memberRepository.findByLoginId(loginId);
            return optionalAccount.map(account -> new UserDTO(
                    account.getLoginId(),
                    account.getUsername(),
                    account.getEmail(),
                    account.getPhoneNumber(),
                    account.getRole().name()
            ));
        } catch (Exception e) {
            log.error("Error while retrieving user info: {}", e.getMessage());
            return Optional.empty();
        }
    }

    public boolean updateUsername(Long loginId, String newUsername) {
        if(newUsername == null || newUsername.isEmpty()) {
            return false;
        }

        Optional<Account> optionalAccount = memberRepository.findByLoginId(loginId);
        if(optionalAccount.isPresent()) {
            memberRepository.updateUsername(loginId, newUsername);
            return true;
        }
        return false;
    }

    public boolean updateEmail(Long loginId, String newEmail) {
        if(newEmail == null || newEmail.isEmpty()) {
            return false;
        }

        if(!isValidEmail(newEmail)) {
            return false;
        }

        Optional<Account> optionalAccount = memberRepository.findByLoginId(loginId);
        if(optionalAccount.isPresent()) {
            memberRepository.updateEmail(loginId, newEmail);
            return true;
        }
        return false;
    }

    public boolean updatePhone(Long loginId, String newPhone) {
        if(newPhone == null || newPhone.isEmpty()) {
            return false;
        }

        if(!isValidPhoneNumber(newPhone)) {
            return false;
        }

        Optional<Account> optionalAccount = memberRepository.findByLoginId(loginId);
        if(optionalAccount.isPresent()) {
            memberRepository.updatePhoneNumber(loginId, newPhone);
            return true;
        }
        return false;
    }



    // 유저 정보 업데이트
    public boolean updateUserInfo(Long loginId, String newEmail, String newPhoneNumber) {
        // 입력 데이터 유효성 검사
        if (!isValidEmail(newEmail) || !isValidPhoneNumber(newPhoneNumber)) {
            return false;
        }

        Optional<Account> accountOpt = memberRepository.findByLoginId(loginId);
        if (accountOpt.isPresent()) {
            Account account = accountOpt.get();
            account.setEmail(newEmail);
            account.setPhoneNumber(newPhoneNumber);

            memberRepository.updateUser(account); // 업데이트 실행
            return true;
        }
        return false;
    }

    // 이메일 유효성 검사
    private boolean isValidEmail(String email) {
        return email != null && email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    }

    // 전화번호 유효성 검사
    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber != null && phoneNumber.matches("^[0-9]{10,15}$");
    }
}


