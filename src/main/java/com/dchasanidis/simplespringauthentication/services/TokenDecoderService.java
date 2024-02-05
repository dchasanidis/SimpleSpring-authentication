package com.dchasanidis.simplespringauthentication.services;

import com.dchasanidis.simplespringauthentication.model.entities.UserEntity;
import com.dchasanidis.simplespringauthentication.repositories.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
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
        final Claims claims = validateSignedToken(token);
        final UserEntity user = userRepository.findByUsername(claims.getSubject()).orElseThrow();
        final UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), user.getAuthorities());
        return new SecurityContextImpl(authenticationToken);
    }

    private Claims validateSignedToken(final String token) {
        final Jws<Claims> claimsJws = parser.parseSignedClaims(token);
        return claimsJws.getPayload();
    }
}
