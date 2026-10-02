package com.eco.auth.utils;

public record ApiResponse<T>(
		String message,
        T data,
        String status
) {

}
