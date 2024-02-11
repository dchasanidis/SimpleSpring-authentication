package com.dchasanidis.simplespringauthentication.services;

import com.dchasanidis.simplespringauthentication.errorHandling.IssueCodes;
import com.dchasanidis.simplespringauthentication.model.RegistrationForm;
import com.dchasanidis.simplespringauthentication.model.User;
import com.dchasanidis.simplespringauthentication.model.dtos.responses.UserMapper;
import com.dchasanidis.simplespringauthentication.model.entities.UserEntity;
import com.dchasanidis.simplespringauthentication.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistrationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final ApplicationExceptionFactory exceptionFactory;

    public RegistrationService(final UserRepository userRepository, final PasswordEncoder passwordEncoder, UserMapper userMapper, ApplicationExceptionFactory exceptionFactory) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.exceptionFactory = exceptionFactory;
    }

    @Transactional
    public User register(final RegistrationForm registrationForm) {
        if (userRepository.existsByEmailOrUsername(registrationForm.getEmail(), registrationForm.getUsername())) {
            throw exceptionFactory.createApplicationException(IssueCodes.USERNAME_OR_EMAIL_EXISTS);
        }

        final UserEntity newUser = UserEntity.fromRegistrationForm(registrationForm);
        newUser.setPassword(passwordEncoder.encode(registrationForm.getPassword()));
        return userMapper.toDto(userRepository.save(newUser));
    }
}
