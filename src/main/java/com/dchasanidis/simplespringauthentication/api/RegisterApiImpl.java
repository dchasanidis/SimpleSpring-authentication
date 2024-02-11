package com.dchasanidis.simplespringauthentication.api;

import com.dchasanidis.simplespringauthentication.model.RegistrationForm;
import com.dchasanidis.simplespringauthentication.model.User;
import com.dchasanidis.simplespringauthentication.services.RegistrationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterApiImpl implements RegisterApiDelegate {

    private final RegistrationService registrationService;

    public RegisterApiImpl(final RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @Override
    public ResponseEntity<User> register(final RegistrationForm registrationForm) {
        return ResponseEntity.ok(registrationService.register(registrationForm));
    }

}

