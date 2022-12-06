package com.example.productservice.exception

import org.springframework.http.HttpStatus

class ProductNotFoundException (field: String): BaseException(
    HttpStatus.NOT_FOUND,
    ApiError(
        type = "NOT_FOUND",
        errors = arrayListOf(
            ErrorDto(
                "CLIENT_NOT_FOUND",
                ErrorType.ERROR,
                "Client not found",
                field))
    )
)