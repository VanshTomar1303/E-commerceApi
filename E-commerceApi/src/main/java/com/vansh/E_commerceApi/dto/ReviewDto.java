package com.vansh.E_commerceApi.dto;

import lombok.Data;

@Data
public class ReviewDto {
    private int id;
    private String review;
    private String email;
    private String productName;
}
