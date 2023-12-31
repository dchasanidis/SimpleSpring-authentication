package com.dchasanidis.simplespringauthentication.config;

import com.dchasanidis.simplespringauthentication.services.TokenDecoderService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class JwtRequestFilter extends OncePerRequestFilter {
    private static final String AUTHORIZATION_HEADER = "authorization";
    private static final String BEARER = "Bearer ";

    private final TokenDecoderService tokenDecoderService;
    private final Set<AntPathRequestMatcher> openEndpoints;


    public JwtRequestFilter(final TokenDecoderService tokenDecoderService, final String[] openEndpoints) {
        this.tokenDecoderService = tokenDecoderService;
        this.openEndpoints = Arrays.stream(openEndpoints).map(AntPathRequestMatcher::new).collect(Collectors.toSet());
    }


    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain) throws ServletException, IOException {
        // Check if the request matches an endpoint that is permitted all
        if (openEndpoints.stream().anyMatch(matcher -> matcher.matches(request))) {
            // Skip further processing and pass the request to the next filter in the chain
            filterChain.doFilter(request, response);
            return;
        }

        final String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER);
        if (authorizationHeader == null || !authorizationHeader.startsWith(BEARER)) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        } else {
            final String token = authorizationHeader.replace(BEARER, "");
            SecurityContext securityContext = tokenDecoderService.getContextFromToken(token);
            SecurityContextHolder.setContext(securityContext);
            filterChain.doFilter(request, response);
        }
    }
}
