package com.example.clientprofile.dtos.password

data class HashPasswordResponse(
    val hash: String,
    val salt: String
)
