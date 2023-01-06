package koxdeda.orderservice.exception

import org.springframework.http.HttpStatus

class ValidationException(field: String): BaseException(
    HttpStatus.BAD_REQUEST,
    ApiError(
        type = "BAD_REQUEST",
        errors = arrayListOf(
            ErrorDto(
                "VALIDATION_ERROR",
                ErrorType.ERROR,
                "Error when validate order parameters",
                field))
            )
        )