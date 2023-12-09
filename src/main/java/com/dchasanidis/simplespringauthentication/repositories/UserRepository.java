package com.dchasanidis.simplespringauthentication.repositories;

import com.dchasanidis.simplespringauthentication.model.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query("SELECT u FROM users u WHERE u.username = ?1")
    Optional<UserEntity> findByUsername(String username);


    @Query("select (count(u) > 0) from users u where u.email = ?1 or u.username = ?2")
    boolean existsByEmailOrUsername(String email, String username);
}
