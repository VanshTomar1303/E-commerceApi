package com.vansh.E_commerceApi.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 1000)
    private String review;

    @ManyToOne
    @JoinColumn(name = "user_email",nullable = false,referencedColumnName = "email")
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_name",nullable = false,referencedColumnName = "name")
    private Product product;

}
