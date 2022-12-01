package com.example.clientprofile.controllers

import com.example.clientprofile.exception.ApiError
import com.example.clientprofile.exception.ErrorDto
import com.example.clientprofile.exception.ErrorType
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import java.util.stream.Collectors
import javax.validation.ConstraintViolationException

@RestControllerAdvice
class ErrorHandlingControllerAdvice {
    private val log = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(ConstraintViolationException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun onConstraintValidationException(
        ex: ConstraintViolationException
    ): ApiError {
        log.info("it checks url")
        val violations: List<ErrorDto> = ex.constraintViolations.stream()
            .map { violation ->
                ErrorDto(
                    code = violation.messageTemplate,
                    type = ErrorType.ERROR,
                    detail = violation.message,
                    field = violation.propertyPath.toString(),
                )
            }.collect(Collectors.toList())
        return ApiError(
            type = "NOT_FOUND",
            errors = violations
        )
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun onMethodArgumentNotValidException(ex: MethodArgumentNotValidException): ApiError {
        log.info("it checks body")
        val result: BindingResult? = ex.bindingResult
        log.info(result.toString())
        val violations: List<ErrorDto> = result?.fieldErrors?.stream()
            ?.map { error ->
                ErrorDto(
                    code = "BAD_REQUEST",
                    type = ErrorType.ERROR,
                    detail = error.field,
                    field = error.field
                )
            }
            ?.collect(Collectors.toList()) as List<ErrorDto>
        return ApiError(
            type = "NOT_FOUND",
            errors = violations
        )
    }
}