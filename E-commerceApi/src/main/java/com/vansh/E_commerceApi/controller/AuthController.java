package com.vansh.E_commerceApi.controller;

import com.vansh.E_commerceApi.dto.LoginUserDto;
import com.vansh.E_commerceApi.dto.RegisterUserDto;
import com.vansh.E_commerceApi.dto.VerificationDto;
import com.vansh.E_commerceApi.model.User;
import com.vansh.E_commerceApi.repo.UserRepo;
import com.vansh.E_commerceApi.response.LoginResponse;
import com.vansh.E_commerceApi.response.Response;
import com.vansh.E_commerceApi.service.AuthService;
import com.vansh.E_commerceApi.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping("/auth")
@RestController
public class AuthController {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthService authService;
    @Autowired
    private UserRepo userRepo;

    @PostMapping("/signup")
    public ResponseEntity<?> register(@RequestBody RegisterUserDto registerUserDto) {
        try{
            Response registerUser = authService.signUp(registerUserDto);
            return ResponseEntity.ok(registerUser);
        }catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginUserDto loginUserDto) {
        try{
            User authenticateUser = authService.authenticate(loginUserDto);
            String jwtToken = jwtService.generateToken(authenticateUser);
            LoginResponse loginResponse = new LoginResponse(jwtToken, jwtService.getExpirationTime(), authenticateUser.getRole());
            return ResponseEntity.ok(loginResponse);
        }catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyUser(@RequestBody VerificationDto verificationDto) {
        try {
            Response response = authService.verifyUser(verificationDto);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/resend-code")
    public ResponseEntity<?> resendVerificationCode(@RequestParam("email") String email) {
        try {
            Response response = authService.resendCode(email);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

}
