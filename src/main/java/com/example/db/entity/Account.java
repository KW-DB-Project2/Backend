package com.example.db.entity;


import lombok.Data;

@Data
public class Account {

    private Long id;
    private Long loginId;

    private String username;
    private String email;

    public Account(){};

    public Account(Long loginId, String username, String email){
        this.loginId = loginId;
        this.username = username;
        this.email = email;
    }


}
