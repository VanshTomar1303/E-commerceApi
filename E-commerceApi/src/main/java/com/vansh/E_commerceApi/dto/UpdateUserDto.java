package com.vansh.E_commerceApi.dto;

import lombok.Data;

@Data
public class UpdateUserDto {
    private String email;
    private String username;
    private String role;
}
