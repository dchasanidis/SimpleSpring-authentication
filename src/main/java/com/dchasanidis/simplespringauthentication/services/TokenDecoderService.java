package com.dchasanidis.simplespringauthentication.services;

import com.dchasanidis.simplespringauthentication.model.entities.UserEntity;
import com.dchasanidis.simplespringauthentication.repositories.UserRepository;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.impl.DefaultClaims;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class TokenDecoderService {

    private final UserRepository userRepository;
    private final JwtParser parser;
    public TokenDecoderService(final UserRepository userRepository, JwtParser parser) {
        this.userRepository = userRepository;
        this.parser = parser;
    }


    public SecurityContext getContextFromToken(final String token) {
        final DefaultClaims defaultClaims = validateTokenWithoutSignature(token);
        final UserEntity user = userRepository.findByUsername(defaultClaims.getSubject()).orElseThrow();
        final UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), user.getAuthorities());
        return new SecurityContextImpl(authenticationToken);
    }

    private DefaultClaims validateTokenWithoutSignature(final String token) {
        final Jwt<?, DefaultClaims> parsed = (Jwt<?, DefaultClaims>) parser.parse(token);
        return parsed.getPayload();
    }
}
