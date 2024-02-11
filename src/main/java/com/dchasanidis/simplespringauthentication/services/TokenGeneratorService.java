package com.dchasanidis.simplespringauthentication.services;

import com.dchasanidis.simplespringauthentication.errorHandling.IssueCodes;
import com.dchasanidis.simplespringauthentication.model.LoginResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.jackson.io.JacksonSerializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class TokenGeneratorService {
    @Qualifier("jwtMapper")
    private final ObjectMapper objectMapper;
    private final PasswordEncoder passwordEncoder;
    private final SecretKeySpec secretKey;

    @Value("${jwt.expiration}")
    private int jwtExpirationMs;
    private final ApplicationExceptionFactory exceptionFactory;


    public TokenGeneratorService(final ObjectMapper objectMapper, final PasswordEncoder passwordEncoder, SecretKeySpec secretKey, ApplicationExceptionFactory exceptionFactory) {
        this.objectMapper = objectMapper;
        this.passwordEncoder = passwordEncoder;
        this.secretKey = secretKey;
        this.exceptionFactory = exceptionFactory;
    }

    public LoginResponse createToken(final UserDetails userDetails, final String password) {
        if (userDetails != null && passwordEncoder.matches(password, userDetails.getPassword())) {
            String token = generateToken(userDetails);
            LoginResponse response = new LoginResponse();
            response.setToken(token);
            return response;
        } else {
            // Handle authentication failure
            throw exceptionFactory.createApplicationException(IssueCodes.AUTHENTICATION_FAILED);
        }
    }

    private String generateToken(final UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", userDetails.getUsername());
        claims.put("created", LocalDateTime.now());

        return Jwts.builder()
                .json(new JacksonSerializer<>(objectMapper))
                .signWith(secretKey)
                .expiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .subject("subject")
                .claims(claims)
                .compact();
    }


}
