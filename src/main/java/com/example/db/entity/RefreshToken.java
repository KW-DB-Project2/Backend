package com.example.db.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class RefreshToken {

    private Long id;
    private Long loginId;
    private String token;
    private Date expiryDate;
}
