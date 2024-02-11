package com.dchasanidis.simplespringauthentication.api;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class UserApiImpl implements UserApiDelegate {

    @Override
    public ResponseEntity<String> getUserStuff() {
        return ResponseEntity.ok("Secured user stuff");
    }
}
