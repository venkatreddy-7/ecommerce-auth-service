package com.eco.auth.service;

import com.eco.auth.dto.request.LoginRequest;
import com.eco.auth.dto.request.RegisterRequest;
import com.eco.auth.dto.response.LoginResponse;
import com.eco.auth.dto.response.UserResponse;

import jakarta.validation.Valid;

public interface AuthService {

	UserResponse register(@Valid RegisterRequest request);

	LoginResponse login(@Valid LoginRequest request);

}
