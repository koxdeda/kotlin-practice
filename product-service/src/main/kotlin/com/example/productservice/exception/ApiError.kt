package com.example.productservice.exception


class ApiError(
    val type: String,
    val errors: List<ErrorDto>) {
}

data class ErrorDto(
    val code: String,
    val type: ErrorType,
    val detail: String,
    val field: String,
)