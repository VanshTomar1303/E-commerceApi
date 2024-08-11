package com.vansh.E_commerceApi.service;

import com.vansh.E_commerceApi.dto.LoginUserDto;
import com.vansh.E_commerceApi.dto.PasswordChangeDto;
import com.vansh.E_commerceApi.dto.RegisterUserDto;
import com.vansh.E_commerceApi.dto.VerificationDto;
import com.vansh.E_commerceApi.model.User;
import com.vansh.E_commerceApi.repo.UserRepo;
import com.vansh.E_commerceApi.response.Response;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

@Service
public class AuthService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private EmailService emailService;

    public Response signUp(RegisterUserDto input){
        Response response = new Response();
        System.out.println(input.getEmail());
        try{
            User user = new User();
           if(!input.getEmail().isEmpty()) {
               user.setUsername(input.getUsername());
               user.setEmail(input.getEmail());
               user.setPassword(passwordEncoder.encode(input.getPassword()));
               user.setRole("ROLE_"+input.getRole());
               user.setVerificationCode(generateVerificationCode());
               user.setVerificationCodeExpiresAt(LocalDateTime.now().plusHours(1));
               user.setEnabled(false);

               sendVerificationEmail(user);
               userRepo.save(user);

               response.setStatusCode(200);
               response.setMessage("Register Successfully");
           }else{
               response.setStatusCode(404);
               response.setMessage("Can't register some fields are empty");
           }
           return response;
        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error occurred due to :" + e.getMessage());
        }
        return response;
    }

    public User authenticate(LoginUserDto input){
        User user = userRepo.findByEmail(input.getEmail())
                .orElseThrow(()->new RuntimeException("User not found"));

        if(!user.isEnabled()){
            throw new RuntimeException("Account not verified");
        }
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );
        return user;
    }

    public Response verifyUser(VerificationDto input){
        Optional<User> optionalUser = userRepo.findByEmail(input.getEmail());
        Response response = new Response();
        try{
            if(optionalUser.isPresent()){
                User user = optionalUser.get();
                if(user.getVerificationCodeExpiresAt().isBefore(LocalDateTime.now())){
                    response.setStatusCode(404);
                    response.setMessage("Verification code has expired");
                    return response;
                }
                if(user.getVerificationCode().equals(input.getVerificationCode())){
                    user.setEnabled(true);
                    user.setVerificationCode(null);
                    user.setVerificationCodeExpiresAt(null);
                    userRepo.save(user);
                    response.setStatusCode(200);
                    response.setMessage("Email verified successfully");
                }else{
                    response.setStatusCode(404);
                    response.setMessage("Verification code is invalid");
                }
                return response;
            }else{
                response.setStatusCode(404);
                response.setMessage("User not found");
            }
            return response;
        }catch(Exception e){
            response.setStatusCode(500);
            response.setMessage("Error occurred due to :" + e.getMessage());
        }
        return response;
    }

    public Response resendCode(String email){
        Response response = new Response();
        Optional<User> optionalUser = userRepo.findByEmail(email);
        try{
            if(optionalUser.isPresent()){
                User user = optionalUser.get();
                if(user.isEnabled()){
                    response.setStatusCode(404);
                    response.setMessage("Account is verified");
                }
                user.setVerificationCode(generateVerificationCode());
                user.setVerificationCodeExpiresAt(LocalDateTime.now().plusMinutes(15));
                sendVerificationEmail(user);
                userRepo.save(user);
                response.setStatusCode(200);
                response.setMessage("Resend verification code successfully");
            }else{
                response.setStatusCode(404);
                response.setMessage("Can't resend verification code");
            }
        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error occurred due to : "+ e.getMessage());
        }
        return response;
    }

    public void sendVerificationEmail(User user) {
        String subject ="Account Verification";
        String verificationCode = "VERIFICATION CODE " + user.getVerificationCode();
        String htmlMessage = "<html>"
                + "<body style=\"font-family: Arial, sans-serif;\">"
                + "<div style=\"background-color: #f5f5f5; padding: 20px;\">"
                + "<h2 style=\"color: #333;\">Welcome to our E-commerce Api!</h2>"
                + "<p style=\"font-size: 16px;\">Please enter the verification code below to continue:</p>"
                + "<div style=\"background-color: #fff; padding: 20px; border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1);\">"
                + "<h3 style=\"color: #333;\">Verification Code:</h3>"
                + "<p style=\"font-size: 18px; font-weight: bold; color: #007bff;\">" + verificationCode + "</p>"
                + "</div>"
                + "</div>"
                + "</body>"
                + "</html>";

        try {
            emailService.sendVerificationEmail(user.getEmail(), subject, htmlMessage);
        } catch (MessagingException e) {
            // Handle email sending exception
            e.printStackTrace();
        }
    }

    private String generateVerificationCode() {
        Random random = new Random();
        int code = random.nextInt(900000)+100000;
        return String.valueOf(code);
    }

    public User findByEmail(String email) {
        Optional<User> userOptional = userRepo.findByEmail(email);
        if(userOptional.isEmpty()){
            throw new RuntimeException("User Not found");
        }
        return userOptional.get();
    }

    public Response changePassword(PasswordChangeDto passwordChangeDto){
        Response response = new Response();
        try{
            Optional<User> userOptional = userRepo.findByEmail(passwordChangeDto.getEmail());

            if(userOptional.isPresent()){
                User user = userOptional.get();
                if(!user.isEnabled()){
                    response.setStatusCode(404);
                    response.setMessage("User is not verified");
                    return response;
                }
                user.setPassword(passwordEncoder.encode(passwordChangeDto.getNewPassword()));
                response.setStatusCode(200);
                response.setMessage("Password Changed Successfully");
                userRepo.save(user);
            }else{
                response.setStatusCode(404);
                response.setMessage("User not found!");
            }
        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error occurred due to : "+e.getMessage());
        }
        return response;
    }
}
