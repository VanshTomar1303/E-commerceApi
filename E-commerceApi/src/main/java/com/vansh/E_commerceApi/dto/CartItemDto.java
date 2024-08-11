package com.vansh.E_commerceApi.dto;

import com.vansh.E_commerceApi.model.Product;
import lombok.Data;

@Data
public class CartItemDto {
    private int id;
    private Product product;
    private int quantity;
}
