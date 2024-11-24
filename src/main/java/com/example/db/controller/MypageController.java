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

import java.util.Map;
import java.util.Optional;


@RestController
public class MypageController {

    @Autowired
    private MypageService mypageService;

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
    public ResponseEntity<?> editUsername(@RequestBody Map<String, String> payload) {
        String name = payload.get("name");
        if (name == null || name.isEmpty()) {
            return ResponseEntity.badRequest().body("Name is required");
        }

        Optional<UserDTO> userInfo = mypageService.getUserInfo();
        if (userInfo.isPresent()) {
            Long id = userInfo.get().getId();
            boolean updateUsername = mypageService.updateUsername(id, name);
            if (updateUsername) {
                return ResponseEntity.ok("Username updated successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }

    @PutMapping("/edit/phoneNumber")
    public ResponseEntity<?> editPhoneNumber(@RequestBody Map<String, String> payload) {
        String phoneNumber = payload.get("phoneNumber");
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            return ResponseEntity.badRequest().body("Phone number is required");
        }

        Optional<UserDTO> userInfo = mypageService.getUserInfo();
        if (userInfo.isPresent()) {
            Long id = userInfo.get().getId();
            boolean updatePhoneNumber = mypageService.updatePhoneNumber(id, phoneNumber);
            if (updatePhoneNumber) {
                return ResponseEntity.ok("Phone number updated successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }



    @PutMapping("/edit/email")
    public ResponseEntity<?> editEmail(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");
        if (email == null || email.isEmpty()) {
            return ResponseEntity.badRequest().body("Email is required");
        }

        Optional<UserDTO> userInfo = mypageService.getUserInfo();
        if (userInfo.isPresent()) {
            Long id = userInfo.get().getId();
            boolean updateEmail = mypageService.updateEmail(id, email);
            if (updateEmail) {
                return ResponseEntity.ok("Email updated successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }

}
