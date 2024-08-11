package com.vansh.E_commerceApi.response;

import lombok.Data;

@Data
public class LoginResponse {
    private String token;
    private long expiresIn;
    private String role;

    public LoginResponse(String token, long expiresIn,String role) {
        this.token = token;
        this.expiresIn = expiresIn;
        this.role = role;
    }
}
