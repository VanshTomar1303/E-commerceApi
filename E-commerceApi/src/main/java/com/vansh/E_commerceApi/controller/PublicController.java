package com.vansh.E_commerceApi.controller;

import com.vansh.E_commerceApi.dto.*;
import com.vansh.E_commerceApi.response.Response;
import com.vansh.E_commerceApi.service.AuthService;
import com.vansh.E_commerceApi.service.CategoryService;
import com.vansh.E_commerceApi.service.ProductService;
import com.vansh.E_commerceApi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/adminuser")
public class PublicController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;
    @Autowired
    private AuthService authService;
    @Autowired
    private UserService userService;

    // get All category
    @GetMapping("/category")
    public ResponseEntity<List<CategoryDto>> getAllCategory(){
        return ResponseEntity.ok(categoryService.getAllCategory());
    }
    //get by category name
    @GetMapping("/category/{name}")
    public ResponseEntity<CategoryDto> getByCategoryName(@PathVariable String name){
        return ResponseEntity.ok(categoryService.getCategoryByName(name));
    }


    //get all products
    @GetMapping("/product")
    public ResponseEntity<List<ProductDto>> getAllProduct(){
        return ResponseEntity.ok(productService.getAllProduct());
    }

    //get product by name
    @GetMapping("/getByProductName/{name}")
    public ResponseEntity<ProductDto> getByProductName(@PathVariable String name){
        return ResponseEntity.ok(productService.findByName(name));
    }

    //get by category name
    @GetMapping("/getByCategory/{category}")
    public ResponseEntity<List<ProductDto>> getByCategoryNAme(@PathVariable String category){
        return ResponseEntity.ok(productService.getByCategory(category));
    }

    //change password
    @PutMapping("/changePassword")
    public ResponseEntity<Response> changePassword(@RequestBody PasswordChangeDto passwordChangeDto){
        return ResponseEntity.ok(authService.changePassword(passwordChangeDto));
    }

    // update the user
    @PutMapping("/updateUserDetails/{email}")
    public ResponseEntity<Response> updateUser(@PathVariable String email, @RequestBody UpdateUserDto updateUserDto){
        return ResponseEntity.ok(userService.updateUser(email, updateUserDto));
    }

    //get current user
    @GetMapping("/currentUser")
    public ResponseEntity<RegisterUserDto> getCurrentUser(){
        return ResponseEntity.ok(userService.getCurrentUser());
    }

}
