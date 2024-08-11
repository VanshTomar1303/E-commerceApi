package com.vansh.E_commerceApi.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "product_name",nullable = false,referencedColumnName = "name")
    private Product cartItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_email",nullable = false,referencedColumnName = "email")
    private User user;

    private int quantity;

}
