package com.example.clientprofile.validators

import com.example.clientprofile.dtos.client.ClientCreateDto
import com.example.clientprofile.exception.ErrorDto
import com.example.clientprofile.exception.ErrorType
import org.springframework.stereotype.Component
import java.util.ArrayList
import java.util.regex.Pattern

@Component
class RequestValidator {

    fun validClientCreate(clientCreateDto: ClientCreateDto): List<ErrorDto>{
        val violations: ArrayList<ErrorDto> = ArrayList()
        if (clientCreateDto.name.isNullOrBlank()) {
            violations.add(ErrorDto("BAD_REQUEST", ErrorType.ERROR, "Invalid name. Name must be non-null", "name"))
        }
        if (clientCreateDto.phone.isNullOrBlank() || !clientCreateDto.phone.isPhoneNumber()) {
            violations.add(ErrorDto("BAD_REQUEST", ErrorType.ERROR, "Invalid phone number. Phone should be in +x(xxx)xxx-xx-xx", "phone"))
        }
        if (!clientCreateDto.email.isNullOrBlank() && !clientCreateDto.email.isEmail()) {
            violations.add(ErrorDto("BAD_REQUEST", ErrorType.ERROR, "Invalid email", "email"))
        }
        if (clientCreateDto.password.isNullOrBlank() || clientCreateDto.password.length < 5) {
            violations.add(ErrorDto("BAD_REQUEST", ErrorType.ERROR, "Invalid password", "password"))
        }
        return violations.toList()
    }

    val validPhonePattern: Pattern = Pattern.compile(validPhoneRegEx)
    val validEmailPattern: Pattern = Pattern.compile(validEmailRegEx)
    fun CharSequence.isPhoneNumber() : Boolean = validPhonePattern.matcher(this).find()
    fun CharSequence.isEmail() : Boolean = validEmailPattern.matcher(this).find()


    companion object {
        const val validPhoneRegEx: String = "^\\+?7(\\d{10})\$"
        const val validEmailRegEx: String = "^[\\w-]+@([\\w-]+\\.)+[\\w-]{2,4}\$"
    }



}