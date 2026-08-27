package com.outridernet.launch.authentication.request;

public record LoginRequest(
        String email,
        String password
) {
}
