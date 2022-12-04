package com.example.clientprofile.service.impl

import com.example.clientprofile.dtos.auth.JwtResponseDto
import com.example.clientprofile.exception.ClientNotFoundException
import com.example.clientprofile.exception.CredentialsNotValidException
import com.example.clientprofile.jwt.JwtAuthentication
import com.example.clientprofile.jwt.JwtFilter
import com.example.clientprofile.jwt.JwtProvider
import com.example.clientprofile.repository.ClientRepository
import com.example.clientprofile.service.AuthService
import com.example.clientprofile.service.PasswordService
import io.jsonwebtoken.Claims
import lombok.NonNull
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import java.text.SimpleDateFormat
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse


@Service
class AuthServiceImpl(
    val clientRepository: ClientRepository,
    val passwordService: PasswordService,
    val jwtProvider: JwtProvider,
): AuthService {

    val refreshStorage: MutableMap<String, String> = HashMap()

    override fun login(phone: String, password: String, response: HttpServletResponse): JwtResponseDto {
        val client = clientRepository.getOneByPhone(phone) ?: throw CredentialsNotValidException("phone")

        return if(passwordService.checkPassword(client.salt!!, client.secretHash!!, password)){ // check password
            val accessToken = jwtProvider.generateAccessToken(client) // generate new access token
            val refreshToken = jwtProvider.generateRefreshToken(client)// generate new refresh token
            refreshStorage[client.phone!!] = refreshToken // save session TODO попробовать заменить на Redis

            val cookie = Cookie("AccessToken", accessToken)
            cookie.path = "/"//устанавливаем путь
            cookie.maxAge = JwtProvider.ACCESS_TOKEN_LIVE_TIME//здесь устанавливается время жизни куки
            response.addCookie(cookie) //добавляем Cookie в запрос

            val cookie1 = Cookie("RefreshToken", refreshToken)
            cookie1.path = "/"//устанавливаем путь
            cookie1.maxAge = JwtProvider.REFRESH_TOKEN_LIVE_TIME //здесь устанавливается время жизни куки
            response.addCookie(cookie1) //добавляем Cookie в запрос
            response.contentType = "text/plain"//устанавливаем контекст

            JwtResponseDto("Bearer",accessToken, refreshToken) // response
        } else{
            throw CredentialsNotValidException("password")
        }
    }

    override fun getAccessToken(@NonNull refreshToken: String): JwtResponseDto {
    if (jwtProvider.validateRefreshToken(refreshToken)) { // Validate refreshToken
        val claims: Claims = jwtProvider.getRefreshClaims(refreshToken)!! // Get refreshToken claims
        val login: String = claims.subject //login == phone
        val saveRefreshToken = refreshStorage[login] // get refresh token from current client session
        if (saveRefreshToken !=null && saveRefreshToken == refreshToken){ // check equals refresh tokens
                val client = clientRepository.getOneByPhone(login) ?: throw ClientNotFoundException("phone")
                val accessToken = jwtProvider.generateAccessToken(client) // generate new access token
                return JwtResponseDto("Bearer", accessToken, null) // response
            }
        }
        return JwtResponseDto("Bearer", null, null)
    }


    override fun refresh(@NonNull refreshToken: String): JwtResponseDto {
        if (jwtProvider.validateRefreshToken(refreshToken)) { // Validate refreshToken
            val claims: Claims = jwtProvider.getRefreshClaims(refreshToken)!! // Get refreshToken claims
            val login: String = claims.subject //login == phone
            val saveRefreshToken = refreshStorage[login] // get refresh token from current client session
            if (saveRefreshToken !=null && saveRefreshToken == refreshToken){ // check equals refresh tokens
                val client = clientRepository.getOneByPhone(login) ?: throw ClientNotFoundException("phone")
                val accessToken = jwtProvider.generateAccessToken(client) // generate new access token
                val newRefreshToken = jwtProvider.generateRefreshToken(client) // generate refresh access token
                refreshStorage[client.phone!!] = newRefreshToken // like delete old session and create new
                return JwtResponseDto("Bearer", accessToken, newRefreshToken) // response
            }
        }
        return JwtResponseDto("Bearer", null, null)
    }


    override fun getAuthInfo(): JwtAuthentication? {
        return SecurityContextHolder.getContext().authentication as JwtAuthentication
    }


}