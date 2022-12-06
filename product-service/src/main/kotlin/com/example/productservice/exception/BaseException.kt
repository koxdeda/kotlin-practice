package com.example.productservice.exception

import com.example.productservice.exception.ApiError
import org.springframework.http.HttpStatus

abstract class BaseException(
    val httpStatus: HttpStatus,
    val apiError: ApiError
): RuntimeException(apiError.type) {

}