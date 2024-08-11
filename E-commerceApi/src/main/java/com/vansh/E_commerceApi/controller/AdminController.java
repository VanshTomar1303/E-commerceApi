package com.vansh.E_commerceApi.controller;

import com.vansh.E_commerceApi.dto.RegisterUserDto;
import com.vansh.E_commerceApi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;

    //get All user
    @GetMapping("/AllUser")
    public ResponseEntity<List<RegisterUserDto>> getAllUser(){
        return ResponseEntity.ok(userService.getAllUser());
    }

    //get user by email
    @GetMapping("{email}")
    public ResponseEntity<RegisterUserDto> getUserByEmail(String email){
        return ResponseEntity.ok(userService.getSingleUser(email));
    }
}
