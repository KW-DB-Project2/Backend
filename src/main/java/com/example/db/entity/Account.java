package com.example.db.entity;


import com.example.db.enums.UserRole;
import lombok.Data;

@Data
public class Account {

    private Long id;
    private Long loginId;

    private String username;
    private String email;
    private UserRole role = UserRole.USER;

    public Account(){};

    public Account(Long loginId, String username, String email, UserRole role) {
        this.loginId = loginId;
        this.username = username;
        this.email = email;
        this.role = role;
    }


}
