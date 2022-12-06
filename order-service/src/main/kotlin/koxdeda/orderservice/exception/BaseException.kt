package koxdeda.orderservice.exception

import org.springframework.http.HttpStatus

abstract class BaseException(
    val httpStatus: HttpStatus,
    val apiError: ApiError
): RuntimeException(apiError.type) {

}