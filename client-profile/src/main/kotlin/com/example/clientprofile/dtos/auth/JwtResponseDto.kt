package com.example.clientprofile.dtos.auth

class JwtResponseDto(
    val type: String = "Bearer",
    val jwtAccessSecret: String?,
    val jwtRefreshSecret: String?,
)