package com.vansh.E_commerceApi.dto;

import lombok.Data;

import java.util.List;

@Data
public class RegisterUserDto {
    private String email;
    private String password;
    private String username;
    private String role;
}
