package com.example.productservice.exception

import org.springframework.http.HttpStatus

class ProductAlreadyExist (field: String): BaseException(
    HttpStatus.BAD_REQUEST,
    ApiError(
        type = "BAD_REQUEST",
        errors = arrayListOf(
            ErrorDto(
                "PRODUCT_ALREADY_EXIST",
                ErrorType.ERROR,
                "Product already exist",
                field))
    )
)