package com.example.clientprofile.controllers

import com.example.clientprofile.dtos.auth.JwtResponseDto
import com.example.clientprofile.dtos.auth.RefreshTokenDto
import com.example.clientprofile.dtos.auth.SignInDto
import com.example.clientprofile.jwt.JwtAuthentication
import com.example.clientprofile.service.AuthService
import lombok.RequiredArgsConstructor
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletResponse


@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
class AuthController(val authService: AuthService) {
    private val log = LoggerFactory.getLogger(javaClass)


    @GetMapping("hello/user")
    fun helloUser(): ResponseEntity<String?>? {
        val authInfo: JwtAuthentication? = authService.getAuthInfo()
        log.info("${authInfo?.isAuthenticated}")
        log.info("${authInfo?.name}")
        return ResponseEntity.ok("Hello user! ${authInfo?.name}")
    }


    @PostMapping("/login")
    @ResponseStatus(HttpStatus.CREATED)
    fun signIn(@RequestBody signInClient: SignInDto, response: HttpServletResponse): JwtResponseDto {

        return authService.login(signInClient.phone, signInClient.password, response)
    }

    @PutMapping("token")
    fun getNewAccessToken(@RequestBody refreshToken: RefreshTokenDto): ResponseEntity<JwtResponseDto?>? {
        val token: JwtResponseDto = authService.getAccessToken(refreshToken.refreshToken)
        return ResponseEntity.ok<JwtResponseDto?>(token)
    }

    @PutMapping("refresh")
    fun getNewRefreshToken(@RequestBody refreshToken: RefreshTokenDto): ResponseEntity<JwtResponseDto?>? {
        val token: JwtResponseDto = authService.refresh(refreshToken.refreshToken)
        return ResponseEntity.ok<JwtResponseDto?>(token)
    }

}