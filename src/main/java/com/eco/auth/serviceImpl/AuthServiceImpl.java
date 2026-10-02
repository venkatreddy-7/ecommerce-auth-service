package com.eco.auth.serviceImpl;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eco.auth.dto.request.LoginRequest;
import com.eco.auth.dto.request.RegisterRequest;
import com.eco.auth.dto.response.LoginResponse;
import com.eco.auth.dto.response.UserResponse;
import com.eco.auth.entity.RefreshToken;
import com.eco.auth.entity.Role;
import com.eco.auth.entity.User;
import com.eco.auth.exception.InvalidCredentialsException;
import com.eco.auth.repository.RefreshTokenRepository;
import com.eco.auth.repository.UserRepository;
import com.eco.auth.security.JwtService;
import com.eco.auth.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final RefreshTokenRepository refreshTokenRepository;

	@Transactional
	public UserResponse register(RegisterRequest request) {

		if (userRepository.existsByEmail(request.email())) {
			throw new IllegalArgumentException("Email already registered");
		}

		User user = User.builder().firstName(request.firstName()).lastName(request.lastName())
				.email(request.email().toLowerCase().trim()).password(passwordEncoder.encode(request.password()))
				.role(Role.CUTSOMER).active(true).createdAt(LocalDateTime.now()).build();

		userRepository.save(user);

		return new UserResponse(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getRole(),
				user.getActive());
	}

	@Override
	@Transactional
	public LoginResponse login(LoginRequest request) {

		User user = userRepository.findByEmail(request.email().toLowerCase().trim())
				.orElseThrow(() -> new InvalidCredentialsException("Invalid email or password"));

		if (!Boolean.TRUE.equals(user.getActive())) {

			throw new InvalidCredentialsException("User account is inactive");
		}

		if (!passwordEncoder.matches(request.password(), user.getPassword())) {

			throw new InvalidCredentialsException("Invalid email or password");
		}

		String accessToken = jwtService.generateAccessToken(user);

		String refreshToken = UUID.randomUUID().toString();

		RefreshToken refreshTokenEntity = RefreshToken.builder().userId(user.getId()).tokenHash(refreshToken)
				.expiresAt(LocalDateTime.now().plusDays(7)).revoked(false).createdAt(LocalDateTime.now()).build();

		refreshTokenRepository.save(refreshTokenEntity);

		return new LoginResponse(user.getId(), user.getEmail(), user.getRole().name(), accessToken, refreshToken);
	}
}
