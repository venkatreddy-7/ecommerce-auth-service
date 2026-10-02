package com.eco.auth.dto.response;

import java.util.UUID;

import com.eco.auth.entity.Role;

public record UserResponse(
		UUID id,

        String firstName,

        String lastName,

        String email,

        Role role,

        Boolean active
) {

}
