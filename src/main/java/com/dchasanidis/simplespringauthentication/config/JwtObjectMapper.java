package com.dchasanidis.simplespringauthentication.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.jackson.io.JacksonDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.spec.SecretKeySpec;


@Configuration
public class JwtObjectMapper {
    @Value("${jwt.secret}")
    private String jwtSecret;

    @Bean
    public SecretKeySpec secreteKeySpec() {
        return new SecretKeySpec(jwtSecret.getBytes(), "HMACSHA256");
    }

    @Bean("jwtMapper")
    public ObjectMapper jwtMapper() {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }

    @Bean
    public JwtParser jwtParser() {
        return Jwts.parser()
                .json(new JacksonDeserializer<>(jwtMapper()))
                .verifyWith(secreteKeySpec())
                .build();
    }
}
