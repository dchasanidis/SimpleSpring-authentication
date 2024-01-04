package com.dchasanidis.simplespringauthentication.api;

import com.dchasanidis.simplespringauthentication.model.dtos.requests.LoginForm;
import com.dchasanidis.simplespringauthentication.model.dtos.requests.RegistrationForm;
import com.dchasanidis.simplespringauthentication.model.dtos.responses.UserDto;
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


    public AuthController(final UserDetailsService userDetailsService, final RegistrationService registrationService, final TokenGeneratorService tokenGeneratorService) {
        this.userDetailsService = userDetailsService;
        this.registrationService = registrationService;
        this.tokenGeneratorService = tokenGeneratorService;
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody LoginForm loginForm) {
        final String username = loginForm.username();
        final String password = loginForm.password();

        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return tokenGeneratorService.createToken(userDetails, password);
    }

    @PostMapping("/register")
    public UserDto register(@RequestBody RegistrationForm registrationForm) {
        return registrationService.register(registrationForm);
    }

}
