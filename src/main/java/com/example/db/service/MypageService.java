package com.example.db.service;

import com.example.db.dto.UserDTO;
import com.example.db.entity.Account;
import com.example.db.jdbc.MemberRepository;
import com.example.db.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class MypageService {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private MemberRepository memberRepository;


    public Optional<UserDTO> getUserInfo(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null || !authentication.isAuthenticated()){
            return Optional.empty();
        }

        String username = authentication.getName();
        Optional<Account> optionalAccount = memberRepository.findByUsername(username);

        return optionalAccount.map(account -> new UserDTO(
                account.getLoginId(),
                account.getUsername(),
                account.getEmail(),
                account.getPhoneNumber(),
                account.getRole().name()
        ));
    }



    /*
    public Optional<UserDTO> getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.empty();
        }

        // 인증에서 loginId를 String으로 받아 Long으로 변환
        try {
            String username = authentication.getName();
            Optional<Account> optionalAccount = memberRepository.findByUsername(username);
            System.out.println("user name = " + username);

            return optionalAccount.map(account -> new UserDTO(
                    account.getLoginId(),
                    account.getUsername(),
                    account.getEmail(),
                    account.getPhoneNumber(),
                    account.getRole().name()
            ));
        } catch (NumberFormatException e) {
            return Optional.empty(); // 인증에서 잘못된 값이 있는 경우 처리
        }
    }
    */


    /*
    public boolean updateUserInfo(Long loginId, String newEmail, String newPhoneNumber){
        Optional<Account> byLoginId = memberRepository.findByLoginId(loginId);

        if(byLoginId.isPresent()){
            Account account = byLoginId.get();
            account.setEmail(newEmail);
            account.setPhoneNumber(newPhoneNumber);
            memberRepository.updateUser(account);
            return true;
        }
        return false;
    }
    */
    public boolean updateUserInfo(Long loginId, String newEmail, String newPhoneNumber) {
        // 최종 검증 (프론트엔드에서 검증했더라도 백엔드에서 다시 확인)
        if (!isValidEmail(newEmail) || !isValidPhoneNumber(newPhoneNumber)) {
            return false; // 잘못된 데이터는 저장하지 않음
        }

        Optional<Account> byLoginId = memberRepository.findByLoginId(loginId);
        if (byLoginId.isPresent()) {
            Account account = byLoginId.get();
            account.setEmail(newEmail);
            account.setPhoneNumber(newPhoneNumber);
            memberRepository.updateUser(account); // 업데이트 수행
            return true;
        }
        return false;
    }

    // 이메일 검증 유틸리티
    private boolean isValidEmail(String email) {
        return email != null && email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    }

    // 전화번호 검증 유틸리티
    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber != null && phoneNumber.matches("^[0-9]{10,15}$");
    }


}


