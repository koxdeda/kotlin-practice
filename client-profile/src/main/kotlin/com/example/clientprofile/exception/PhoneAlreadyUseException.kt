package com.example.clientprofile.exception

import org.springframework.http.HttpStatus

class PhoneAlreadyUseException (field: String): BaseException(
    HttpStatus.BAD_REQUEST,
    ApiError(
        type = "BAD_REQUEST",
        errors = arrayListOf(
            ErrorDto(
                "PHONE_ALREADY_IN_USE",
                ErrorType.ERROR,
                "Phone already in use",
                field))
    )
)

