package com.vansh.E_commerceApi.dto;

import lombok.Data;

import java.util.List;

@Data
public class CartDto {
    private List<CartItemDto> cartItemList;
    private double totalAmount;
}
