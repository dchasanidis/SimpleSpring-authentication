package com.dchasanidis.simplespringauthentication.services;

import com.dchasanidis.simplespringauthentication.errorHandling.IssueCodes;
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
    private final ApplicationExceptionFactory exceptionFactory;

    public TokenDecoderService(final UserRepository userRepository, JwtParser parser, ApplicationExceptionFactory exceptionFactory) {
        this.userRepository = userRepository;
        this.parser = parser;
        this.exceptionFactory = exceptionFactory;
    }


    public SecurityContext getContextFromToken(final String token) {
        final Claims claims = validateSignedTokenAndExtractClaims(token);
        final UserEntity user = userRepository.findByUsername(claims.getSubject()).orElseThrow();
        final UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), user.getAuthorities());
        return new SecurityContextImpl(authenticationToken);
    }

    private Claims validateSignedTokenAndExtractClaims(final String token) {
        try {
            final Jws<Claims> claimsJws = parser.parseSignedClaims(token);
            return claimsJws.getPayload();
        } catch (RuntimeException e) {
            throw exceptionFactory.createApplicationException(IssueCodes.AUTHENTICATION_FAILED);
        }
    }
}
