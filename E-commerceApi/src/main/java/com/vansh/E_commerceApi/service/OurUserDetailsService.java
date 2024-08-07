package com.vansh.E_commerceApi.service;

import com.vansh.E_commerceApi.model.User;
import com.vansh.E_commerceApi.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OurUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        // Find the user by username or email
        Optional<User> userOptional = userRepo.findByUsername(usernameOrEmail);
        if (userOptional.isEmpty()) {
            userOptional = userRepo.findByEmail(usernameOrEmail);
        }

        User user = userOptional.orElseThrow(() ->
                new UsernameNotFoundException("User not found with username or email: " + usernameOrEmail));

        return user;
    }
}
