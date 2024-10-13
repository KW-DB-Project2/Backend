package com.example.db.entity;


import lombok.Data;

@Data
public class Account {

    private Long id;
    private Long loginId;

    private String username;
    private String email;


}
