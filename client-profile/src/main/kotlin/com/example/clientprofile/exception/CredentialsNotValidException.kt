package com.example.clientprofile.exception

import org.springframework.http.HttpStatus

class CredentialsNotValidException(field: String): BaseException(
    HttpStatus.BAD_REQUEST,
    ApiError(
        type = "BAD_REQUEST",
        errors = arrayListOf(
            ErrorDto(
                "CREDENTIALS_NOT_VALID",
                ErrorType.ERROR,
                "Credentials not valid",
                field))
    )
)