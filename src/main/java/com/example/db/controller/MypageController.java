package com.example.db.controller;


import com.example.db.dto.UserDTO;
import com.example.db.entity.Account;
import com.example.db.jdbc.MemberRepository;
import com.example.db.service.MypageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
public class MypageController {

    @Autowired
    private MypageService mypageService;

    /*
    @GetMapping("/user-info")
    public ResponseEntity<?> getUserInfo(){
        Optional<UserDTO> userInfo = mypageService.getUserInfo();

        if(userInfo.isPresent()){
            return ResponseEntity.ok(userInfo.get());
        }else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }
    }*/

    @GetMapping("/api/user-info")
    public ResponseEntity<?> getUserInfo() {
        Optional<UserDTO> accountOptional = mypageService.getUserInfo();

        if (accountOptional.isPresent()) {
            return ResponseEntity.ok(accountOptional.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    @GetMapping("/mypage")
    public ResponseEntity<?> getMypage() {
        Optional<UserDTO> accountOptional = mypageService.getUserInfo();

        if (accountOptional.isPresent()) {
            return ResponseEntity.ok(accountOptional.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    @PutMapping("/edit/name")
    public ResponseEntity<?> editUsername(@RequestParam String name) {
        Optional<UserDTO> userInfo = mypageService.getUserInfo();
        if(userInfo.isPresent()) {
            Long loginId = userInfo.get().getLoginId();
            boolean updateUsername = mypageService.updateUsername(loginId, name);
            if(updateUsername) {
                return ResponseEntity.ok("username updated successfully");
            }else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }

    @PutMapping("/edit/email")
    public ResponseEntity<?> editEmail(@RequestParam String email) {
        Optional<UserDTO> userInfo = mypageService.getUserInfo();
        if(userInfo.isPresent()) {
            Long loginId = userInfo.get().getLoginId();
            boolean updateEmail = mypageService.updateEmail(loginId, email);
            if(updateEmail) {
                return ResponseEntity.ok("email updated successfully");
            }else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }

    @PutMapping("/edit/phone")
    public ResponseEntity<?> editPhone(@RequestParam String phoneNumber) {
        Optional<UserDTO> userInfo = mypageService.getUserInfo();
        if(userInfo.isPresent()) {
            Long loginId = userInfo.get().getLoginId();
            boolean updatePhone = mypageService.updatePhone(loginId, phoneNumber);
            if(updatePhone) {
                return ResponseEntity.ok("phoneNumber updated successfully");
            }else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }


    /*
    @PutMapping("/edit/user-info")
    public ResponseEntity<?> updateUserInfo(@RequestParam String email, @RequestParam String phoneNumber) {
        Optional<UserDTO> userInfo = mypageService.getUserInfo();
        if (userInfo.isPresent()) {
            Long loginId = userInfo.get().getLoginId();
            boolean checkUpdate = mypageService.updateUserInfo(loginId, email, phoneNumber);
            if (checkUpdate) {
                return ResponseEntity.ok(userInfo.get());
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid email or phone number format");
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }
    */

}
