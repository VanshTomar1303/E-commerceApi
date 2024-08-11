package com.vansh.E_commerceApi.response;

import lombok.Data;

@Data
public class Response {
    // errors and message
    private String message;
    private int statusCode;
}
