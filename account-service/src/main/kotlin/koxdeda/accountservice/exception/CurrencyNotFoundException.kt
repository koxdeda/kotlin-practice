package koxdeda.accountservice.exception

import org.springframework.http.HttpStatus

class CurrencyNotFoundException(field: String): BaseException(
    HttpStatus.NOT_FOUND,
    ApiError(
        type = "NOT_FOUND",
        errors = arrayListOf(
            ErrorDto(
                "CURRENCY_NOT_FOUND",
                ErrorType.ERROR,
                "Currency not found",
                field))
    )
)