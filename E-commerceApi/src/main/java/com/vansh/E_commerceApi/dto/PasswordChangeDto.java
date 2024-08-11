package com.vansh.E_commerceApi.dto;

import lombok.Data;

@Data
public class PasswordChangeDto {
    private String email;
    private String newPassword;
}
