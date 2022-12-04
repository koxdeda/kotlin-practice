package com.example.clientprofile.jwt

import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import org.springframework.web.filter.GenericFilterBean
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest


@Slf4j
@Component
@RequiredArgsConstructor
class JwtFilter(private val jwtProvider: JwtProvider) : GenericFilterBean() {
    private val log = LoggerFactory.getLogger(javaClass)

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(request: ServletRequest, response: ServletResponse, fc: FilterChain) {
        log.info("Do filter")
        val token = getTokenFromRequest(request as HttpServletRequest)
        if (token != null && jwtProvider.validateAccessToken(token)) {
            val claims = jwtProvider.getAccessClaims(token)
            val jwtInfoToken = JwtAuthentication()
            jwtInfoToken.isAuthenticated = true
            jwtInfoToken.name = claims!!["name", String::class.java]
            SecurityContextHolder.getContext().authentication = jwtInfoToken // set authenticated flag to SecurityContextHolder
        }
        fc.doFilter(request, response)
    }

    private fun getTokenFromRequest(request: HttpServletRequest): String? {
        val bearer = request.getHeader(AUTHORIZATION)
        return if (StringUtils.hasText(bearer) && bearer.startsWith("Bearer ")) {
            bearer.substring(7)
        } else null
    }

    companion object {
        private const val AUTHORIZATION = "Authorization"
    }
}