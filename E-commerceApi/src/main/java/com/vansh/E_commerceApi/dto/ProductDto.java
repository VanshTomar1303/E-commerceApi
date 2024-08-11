package com.vansh.E_commerceApi.dto;

import lombok.Data;

@Data
public class ProductDto {
    private String name;
    private double price;
    private int quantity;
    private String imageLink;
    private String categoryName;

    private int statusCode;
    private String message;
}
