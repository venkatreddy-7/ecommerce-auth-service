package com.eco.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eco.auth.dto.request.LoginRequest;
import com.eco.auth.dto.request.RegisterRequest;
import com.eco.auth.dto.response.LoginResponse;
import com.eco.auth.dto.response.UserResponse;
import com.eco.auth.service.AuthService;
import com.eco.auth.utils.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	@PostMapping("/register")
	public ResponseEntity<ApiResponse<UserResponse>> register(@Valid @RequestBody RegisterRequest request) {

		UserResponse response = authService.register(request);

		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ApiResponse<>("User registered successfully", response, "SUCCESS"));
	}

	@PostMapping("/login")
	public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {

		LoginResponse response = authService.login(request);

		return ResponseEntity.ok(new ApiResponse<>("Login successful", response, "SUCCESS"));
	}
}