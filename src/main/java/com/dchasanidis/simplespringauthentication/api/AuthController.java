package com.dchasanidis.simplespringauthentication.api;

import com.dchasanidis.simplespringauthentication.model.dtos.requests.RegistrationForm;
import com.dchasanidis.simplespringauthentication.model.entities.UserEntity;
import com.dchasanidis.simplespringauthentication.services.RegistrationService;
import com.dchasanidis.simplespringauthentication.services.TokenGeneratorService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AuthController {
    private final UserDetailsService userDetailsService;
    private final RegistrationService registrationService;
    private final TokenGeneratorService tokenGeneratorService;


    public AuthController(UserDetailsService userDetailsService, final RegistrationService registrationService, final TokenGeneratorService tokenGeneratorService) {
        this.userDetailsService = userDetailsService;
        this.registrationService = registrationService;
        this.tokenGeneratorService = tokenGeneratorService;
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> loginRequest) {
        final String username = loginRequest.get("username");
        final String password = loginRequest.get("password");

        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return tokenGeneratorService.createToken(userDetails, password);
    }

    @PostMapping("/register")
    public UserEntity register(@RequestBody RegistrationForm registrationForm) {
        return registrationService.register(registrationForm);
    }

}
