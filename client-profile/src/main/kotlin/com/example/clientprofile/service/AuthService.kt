package com.example.clientprofile.service

import com.example.clientprofile.dtos.auth.JwtResponseDto
import com.example.clientprofile.jwt.JwtAuthentication
import javax.servlet.http.HttpServletResponse

interface AuthService {
    fun login(phone: String, password: String, response: HttpServletResponse): JwtResponseDto
    fun getAccessToken(refreshToken: String): JwtResponseDto
    fun refresh(refreshToken: String): JwtResponseDto
    fun getAuthInfo(): JwtAuthentication?

}