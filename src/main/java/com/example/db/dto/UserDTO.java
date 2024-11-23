package com.example.db.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {

    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private String role;

    public UserDTO(Long id, String name, String email, String phoneNumber, String role){
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

}
