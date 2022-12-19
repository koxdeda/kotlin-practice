package com.example.clientprofile.dtos.client

import org.springframework.validation.annotation.Validated
import javax.validation.constraints.NotBlank

@Validated
class ClientUpdateDto(
    @NotBlank
    val name: String,
    val phone: String,
    val email: String,
)
