package com.example.db.dto;

public class LoginRequestDTO {
    private String localId;
    private String password;

    // Getters and setters
    public String getLocalId() {
        return localId;
    }

    public void setLocalId(String localId) {
        this.localId = localId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

