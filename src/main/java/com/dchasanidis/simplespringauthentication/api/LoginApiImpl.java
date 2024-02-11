package com.dchasanidis.simplespringauthentication.api;

import com.dchasanidis.simplespringauthentication.model.LoginForm;
import com.dchasanidis.simplespringauthentication.model.LoginResponse;
import com.dchasanidis.simplespringauthentication.services.TokenGeneratorService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginApiImpl implements LoginApiDelegate {
    private final UserDetailsService userDetailsService;
    private final TokenGeneratorService tokenGeneratorService;


    public LoginApiImpl(final UserDetailsService userDetailsService, final TokenGeneratorService tokenGeneratorService) {
        this.userDetailsService = userDetailsService;
        this.tokenGeneratorService = tokenGeneratorService;
    }

    @Override
    public ResponseEntity<LoginResponse> login(@RequestBody LoginForm loginForm) {
        final String username = loginForm.getUsername();
        final String password = loginForm.getPassword();

        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return ResponseEntity.ok(tokenGeneratorService.createToken(userDetails, password));
    }
}
