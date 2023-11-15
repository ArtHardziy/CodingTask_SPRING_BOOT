package com.expertsoft.phoneshop.service;

import com.expertsoft.phoneshop.persistence.model.dto.SignInRequest;
import com.expertsoft.phoneshop.persistence.repository.PhoneshopUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationService {

    private AuthenticationManager authenticationManager;
    private PhoneshopUserRepository userRepo;

    public String signin(SignInRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        var user = userRepo.findByEmail(request.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Invalid username or password"));

        return "phoneListPage";
    }
}
