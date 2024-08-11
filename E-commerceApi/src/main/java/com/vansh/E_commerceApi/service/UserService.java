package com.vansh.E_commerceApi.service;

import com.vansh.E_commerceApi.dto.RegisterUserDto;
import com.vansh.E_commerceApi.dto.UpdateUserDto;
import com.vansh.E_commerceApi.model.User;
import com.vansh.E_commerceApi.repo.UserRepo;
import com.vansh.E_commerceApi.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

    // get All user
    public List<RegisterUserDto> getAllUser(){
        List<User> userList = userRepo.findAll();
        List<RegisterUserDto> registerUserDto = userList.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

        return registerUserDto;
    }

    // get current user
    public RegisterUserDto getCurrentUser(){
        // Get the currently authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof UserDetails)) {
            throw new UsernameNotFoundException("User not authenticated");
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername(); // or getEmail() if you store emails

        // Retrieve user entity from the database
        Optional<User> userOptional = userRepo.findByUsername(username);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            return convertToDto(user);
        }
        else{
            throw new UsernameNotFoundException("User not found");
        }
    }

    // get user by email
    public RegisterUserDto getSingleUser(String email){
        Optional<User> userOptional = userRepo.findByEmail(email);
        if(userOptional.isEmpty()){
            throw new RuntimeException("User not found");
        }
        RegisterUserDto dto = new RegisterUserDto();
        User user = userOptional.get();

        dto.setEmail(user.getEmail());
        dto.setUsername(user.getUsername());
        dto.setRole(user.getRole());

        return dto;
    }

    // update user details
    public Response updateUser(String email, UpdateUserDto updateUserDto){
        Response response = new Response();
        try{
            Optional<User> userOptional = userRepo.findByEmail(email);
            if (userOptional.isPresent()){
                User user = userOptional.get();
                user.setUsername(updateUserDto.getUsername());
                user.setEmail(updateUserDto.getEmail());
                user.setRole(updateUserDto.getRole());

                userRepo.save(user);

                response.setStatusCode(200);
                response.setMessage("User updated Successfully");
            }else{
                response.setStatusCode(404);
                response.setMessage("User not found");
            }
        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error occurred due to : "+e.getMessage());
        }
        return response;
    }

    private RegisterUserDto convertToDto(User user) {
        RegisterUserDto dto = new RegisterUserDto();

        dto.setRole(user.getRole());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());

        return dto;
    }
}
