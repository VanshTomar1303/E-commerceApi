package com.vansh.E_commerceApi.controller;

import com.vansh.E_commerceApi.dto.ProductDto;
import com.vansh.E_commerceApi.response.Response;
import com.vansh.E_commerceApi.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class ProductController {

    @Autowired
    private ProductService service;

    //get all products
    @GetMapping("/product")
    public ResponseEntity<List<ProductDto>> getAllProduct(){
        return ResponseEntity.ok(service.getAllProduct());
    }

    //add product
    @PostMapping("/product")
    public ResponseEntity<Response> addProduct(@RequestBody ProductDto productDto){
        return ResponseEntity.ok(service.addProduct(productDto));
    }

    //delete Product
    @DeleteMapping("/product/{name}")
    public ResponseEntity<Response> deleteProduct(@PathVariable String name){
        return ResponseEntity.ok(service.deleteProduct(name));
    }

    //update product
    @PutMapping("/product/{name}")
    public ResponseEntity<Response> updateProduct(@PathVariable String name,@RequestBody ProductDto productDto){
        return ResponseEntity.ok(service.updateProduct(name,productDto));
    }

    //get product by name
    @GetMapping("/getByProductName/{name}")
    public ResponseEntity<ProductDto> getByProductName(@PathVariable String name){
        return ResponseEntity.ok(service.findByName(name));
    }

    //get by category name
    @GetMapping("/getByCategory/{category}")
    public ResponseEntity<List<ProductDto>> getByCategoryNAme(@PathVariable String category){
       return ResponseEntity.ok(service.getByCategory(category));
    }
}
