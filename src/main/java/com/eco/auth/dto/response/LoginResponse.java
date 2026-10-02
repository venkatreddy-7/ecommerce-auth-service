package com.eco.auth.dto.response;

import java.util.UUID;

public record LoginResponse( UUID userId,

        String email,

        String role,

        String accessToken,

        String refreshToken) {

}
