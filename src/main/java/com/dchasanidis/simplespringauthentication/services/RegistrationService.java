package com.dchasanidis.simplespringauthentication.services;

import com.dchasanidis.simplespringauthentication.model.entities.UserEntity;
import com.dchasanidis.simplespringauthentication.repositories.UserRepository;
import com.dchasanidis.simplespringauthentication.model.dtos.requests.RegistrationForm;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistrationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public RegistrationService(final UserRepository userRepository, final PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UserEntity register(final RegistrationForm registrationForm) {
        if (userRepository.existsByEmailOrUsername(registrationForm.email(), registrationForm.username())) {
            throw new RuntimeException("Username or email already exists");
        }
        return userRepository.save(UserEntity.fromRegistrationForm(registrationForm)
                .setPassword(passwordEncoder.encode(registrationForm.password()))
        );
    }
}
