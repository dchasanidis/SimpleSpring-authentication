package com.dchasanidis.simplespringauthentication.model.dtos.requests;

public record RegistrationForm(
        String username,
        String email,
        String password
) {
}
