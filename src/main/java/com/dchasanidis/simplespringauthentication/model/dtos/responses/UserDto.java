package com.dchasanidis.simplespringauthentication.model.dtos.responses;

import com.dchasanidis.simplespringauthentication.model.enums.Authority;

import java.util.Set;

public record UserDto(String username,
                      String email,
                      Set<Authority> authorities) {
}
