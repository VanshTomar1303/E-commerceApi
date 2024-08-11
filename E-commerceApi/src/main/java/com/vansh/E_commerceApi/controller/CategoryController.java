package com.vansh.E_commerceApi.controller;

import com.vansh.E_commerceApi.dto.CategoryDto;
import com.vansh.E_commerceApi.response.Response;
import com.vansh.E_commerceApi.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class CategoryController {
    @Autowired
    private CategoryService service;

    //add a category
    @PostMapping("/category")
    public ResponseEntity<Response> addCategory(@RequestBody CategoryDto categoryDto){
        return ResponseEntity.ok(service.addCategory(categoryDto));
    }

    //delete a category
    @DeleteMapping("/category/{name}")
    public ResponseEntity<Response> deleteCategory(@PathVariable String name){
        return ResponseEntity.ok(service.deleteCategory(name));
    }

    //update product
    @PutMapping("/category/{name}")
    public ResponseEntity<Response> updateCategory(@PathVariable String name,@RequestBody CategoryDto categoryDto){
        return ResponseEntity.ok(service.updateCategory(name,categoryDto));
    }


}
