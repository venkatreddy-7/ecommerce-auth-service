package com.eco.auth.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.eco.auth.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	private final SecretKey secretKey;

	private final long accessTokenExpiration;

	public JwtService(

			@Value("${jwt.secret}") String secret,

			@Value("${jwt.access-token-expiration}") long accessTokenExpiration

	) {

		this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

		this.accessTokenExpiration = accessTokenExpiration;
	}

	public String generateAccessToken(User user) {

		Date now = new Date();

		Date expiration = new Date(now.getTime() + accessTokenExpiration);

		return Jwts.builder()

				.subject(user.getId().toString())

				.claim("email", user.getEmail())

				.claim("role", user.getRole().name())

				.issuedAt(now)

				.expiration(expiration)

				.signWith(secretKey)

				.compact();
	}

	public Claims extractClaims(String token) {

		return Jwts.parser()

				.verifyWith(secretKey)

				.build()

				.parseSignedClaims(token)

				.getPayload();
	}
}