package com.example.clientprofile.exception

import org.springframework.http.HttpStatus

class ValidationError(violations: List<ErrorDto>): BaseException(
    HttpStatus.BAD_REQUEST,
    ApiError(
        type = "BAD_REQUEST",
        errors = violations
    )
)