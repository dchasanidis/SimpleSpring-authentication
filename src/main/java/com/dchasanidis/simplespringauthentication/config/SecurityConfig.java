package com.dchasanidis.simplespringauthentication.config;

import com.dchasanidis.simplespringauthentication.services.TokenDecoderService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private static final String ADMIN = "ADMIN";
    private static final String USER = "USER";
    private final TokenDecoderService tokenDecoderService;

    public SecurityConfig(TokenDecoderService tokenDecoderService) {
        this.tokenDecoderService = tokenDecoderService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorizeHttpRequests) ->
                        authorizeHttpRequests
                                .requestMatchers(openEndpoints()).permitAll()
                                .requestMatchers("/user/**").hasRole(USER)
                                .requestMatchers("/admin/**").hasRole(ADMIN)
                                .requestMatchers(HttpMethod.DELETE).hasRole(ADMIN)
                                .anyRequest().authenticated()
                )
                .addFilterBefore(jwtRequestFilter(), AuthorizationFilter.class)
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }

    @Bean
    public String[] openEndpoints() {
        return new String[]{"/register", "/login"};
    }

    @Bean
    public JwtRequestFilter jwtRequestFilter() {
        return new JwtRequestFilter(tokenDecoderService, openEndpoints());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
