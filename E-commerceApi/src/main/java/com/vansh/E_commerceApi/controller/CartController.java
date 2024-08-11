package com.vansh.E_commerceApi.controller;

import com.vansh.E_commerceApi.dto.AddToCartDto;
import com.vansh.E_commerceApi.dto.CartDto;
import com.vansh.E_commerceApi.model.User;
import com.vansh.E_commerceApi.repo.UserRepo;
import com.vansh.E_commerceApi.response.Response;
import com.vansh.E_commerceApi.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/adminuser")
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private UserRepo userRepo;


    @PostMapping("/cart")
    public ResponseEntity<Response> addItemInCart(@RequestBody AddToCartDto addToCartDto){
        return ResponseEntity.ok(cartService.addToCart(addToCartDto));
    }

    @GetMapping("/cart/{email}")
    public ResponseEntity<CartDto> getAllCart(@PathVariable String email){

        Optional<User> userOptional = userRepo.findByEmail(email);
        if(userOptional.isEmpty()){
            throw new RuntimeException("User not found");
        }
        User user = userOptional.get();
        CartDto cartDto = cartService.listCartItem(user);

        return ResponseEntity.ok(cartDto);
    }

    @DeleteMapping("/cart/{id}")
    public ResponseEntity<Response> deleteCartItem(@PathVariable int id,@RequestParam("email") String email){
        Optional<User> userOptional = userRepo.findByEmail(email);
        if(userOptional.isEmpty()){
            throw new RuntimeException("User not found");
        }
        User user = userOptional.get();
        return ResponseEntity.ok(cartService.deleteCartItem(id,user));
    }
}
