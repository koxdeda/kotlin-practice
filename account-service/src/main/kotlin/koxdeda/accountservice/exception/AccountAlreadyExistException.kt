package koxdeda.accountservice.exception

import org.springframework.http.HttpStatus

class AccountAlreadyExistException (field: String): BaseException(
    HttpStatus.BAD_REQUEST,
    ApiError(
        type = "BAD_REQUEST",
        errors = arrayListOf(
            ErrorDto(
                "ACCOUNT_ALREADY_EXIST",
                ErrorType.ERROR,
                "Account already exists",
                field))
    )
)