package com.eco.auth.entity;

import jakarta.persistence.*;

import lombok.*;

import java.time.LocalDateTime;

import java.util.UUID;

@Entity
@Table(
        name = "refresh_tokens"
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(
            nullable = false
    )
    private UUID userId;

    @Column(
            nullable = false,
            unique = true,
            length = 255
    )
    private String tokenHash;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    @Column(nullable = false)
    private Boolean revoked;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime revokedAt;
}