package koxdeda.accountservice.exception

import org.springframework.http.HttpStatus

class AccountNotFoundException(field: String): BaseException(
    HttpStatus.NOT_FOUND,
    ApiError(
        type = "NOT_FOUND",
        errors = arrayListOf(
            ErrorDto(
                "ACCOUNT_NOT_FOUND",
                ErrorType.ERROR,
                "Account not found",
                field))
    )
)