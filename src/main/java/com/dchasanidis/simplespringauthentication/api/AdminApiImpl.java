package com.dchasanidis.simplespringauthentication.api;

import com.dchasanidis.simplespringauthentication.model.AdminStuff;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class AdminApiImpl implements AdminApiDelegate {
    @Override
    public ResponseEntity<AdminStuff> getAdminStuff() {
        return ResponseEntity.ok(new AdminStuff().item("Admin stuff"));
    }
}
