package com.vansh.E_commerceApi.dto;

import lombok.Data;

@Data
public class VerificationDto {
    private String email;
    private String verificationCode;

    // errors
    private String message;
    private int statusCode;
}
