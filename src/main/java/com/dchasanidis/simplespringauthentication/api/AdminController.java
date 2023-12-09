package com.dchasanidis.simplespringauthentication.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {
    @GetMapping("/admin/stuff")
    public ResponseEntity<?> getAdminStuff() {
        return ResponseEntity.ok("Admin stuff");
    }
}
