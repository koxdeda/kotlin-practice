package com.example.clientprofile.service

import com.example.clientprofile.dtos.password.HashPasswordResponse

interface PasswordService {

    fun hashPassword(password: String): HashPasswordResponse
    fun checkPassword(salt: String, hashPassword: String, password: String): Boolean

}