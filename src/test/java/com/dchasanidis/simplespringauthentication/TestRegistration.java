package com.dchasanidis.simplespringauthentication;

import com.dchasanidis.simplespringauthentication.model.RegistrationForm;
import com.dchasanidis.simplespringauthentication.model.entities.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class TestRegistration {
    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    public String getUrl(final String mapping) {
        return "http://localhost:" + port + mapping;
    }


    @Test
    void testSuccessfulRegistration() {
        final RegistrationForm registrationForm = new RegistrationForm()
                .username("dchasanidis1")
                .email("dimitrischasanidis1@gmail.com")
                .password("password1");
        final ResponseEntity<UserEntity> response = this.restTemplate.postForEntity(getUrl("/register"), registrationForm, UserEntity.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    }
}
