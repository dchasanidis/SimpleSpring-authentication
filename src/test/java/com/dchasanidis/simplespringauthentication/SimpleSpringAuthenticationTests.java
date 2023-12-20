package com.dchasanidis.simplespringauthentication;

import com.dchasanidis.simplespringauthentication.model.entities.UserEntity;
import com.dchasanidis.simplespringauthentication.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class SimpleSpringAuthenticationTests {
    @Autowired
    UserRepository userRepository;

    @Test
    void contextLoads() {
        userRepository.save(new UserEntity().setEmail("email").setPassword("password").setUsername("username"));
    }

}
