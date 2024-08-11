package com.vansh.E_commerceApi.dto;

import lombok.Data;

@Data
public class AddToCartDto {
    private String productName;
    private String email;
    private Integer quantity;
}
