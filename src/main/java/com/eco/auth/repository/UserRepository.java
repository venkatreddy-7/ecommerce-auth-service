package com.eco.auth.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eco.auth.entity.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Repository
public interface UserRepository extends JpaRepository<User, UUID>{

	boolean existsByEmail(@NotBlank @Email String email);

	Optional<User> findByEmail(String email);

}
