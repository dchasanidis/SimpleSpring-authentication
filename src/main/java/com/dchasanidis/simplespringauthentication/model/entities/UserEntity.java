package com.dchasanidis.simplespringauthentication.model.entities;

import com.dchasanidis.simplespringauthentication.model.dtos.requests.RegistrationForm;
import com.dchasanidis.simplespringauthentication.model.enums.Authority;
import jakarta.persistence.*;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;

@Entity(name = "users")
public class UserEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "username", unique = true, nullable = false, updatable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<Authority> authorities;

    @Column(name = "non_expired", nullable = false)
    private boolean isAccountNonExpired = Boolean.TRUE;

    @Column(name = "non_locked", nullable = false)
    private boolean isAccountNonLocked = Boolean.TRUE;

    @Column(name = "enabled", nullable = false)
    private boolean isEnabled = Boolean.TRUE;


    @Override
    public Set<Authority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return this.isEnabled;
    }

    public Long getId() {
        return id;
    }

    public UserEntity setUsername(final String username) {
        this.username = username;
        return this;
    }

    public UserEntity setPassword(final String password) {
        this.password = password;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserEntity setEmail(final String email) {
        this.email = email;
        return this;
    }

    public UserEntity setAuthorities(final Set<Authority> authorities) {
        this.authorities = authorities;
        return this;
    }

    public UserEntity setAccountNonExpired(final boolean accountNonExpired) {
        isAccountNonExpired = accountNonExpired;
        return this;
    }

    public UserEntity setAccountNonLocked(final boolean accountNonLocked) {
        isAccountNonLocked = accountNonLocked;
        return this;
    }

    public UserEntity setEnabled(final boolean enabled) {
        isEnabled = enabled;
        return this;
    }

    public static UserEntity fromRegistrationForm(RegistrationForm registrationForm) {
        return new UserEntity()
                .setUsername(registrationForm.username())
                .setEmail(registrationForm.email())
                .setAuthorities(Set.of(Authority.USER))
                ;
    }
}
