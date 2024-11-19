package com.example.db.dto;

import com.example.db.entity.Account;
import lombok.Data;

@Data
public class LoginResponseDTO {

    private boolean loginSuccess;
    private boolean isNewUser;
    private Account account;
    private String jwtToken;
    private String refreshToken;

}
