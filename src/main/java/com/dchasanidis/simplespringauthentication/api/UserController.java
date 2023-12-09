package com.dchasanidis.simplespringauthentication.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @GetMapping("/user/stuff")
    public ResponseEntity<String> getUserStuff() {
        return ResponseEntity.ok("User stuff");
    }
}
