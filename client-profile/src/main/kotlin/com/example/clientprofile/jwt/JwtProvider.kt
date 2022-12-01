package com.example.clientprofile.jwt

import com.example.clientprofile.model.Client
import io.jsonwebtoken.*
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import lombok.NonNull
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.security.Key
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import javax.crypto.SecretKey


@Slf4j
@Component
class JwtProvider(
    @Value("\${jwt.secret.access}") jwtAccessSecret: String?,
    @Value("\${jwt.secret.refresh}") jwtRefreshSecret: String?
){

    private val log = LoggerFactory.getLogger(javaClass)
    private val jwtAccessSecret: SecretKey
    private val jwtRefreshSecret: SecretKey

    init {
        this.jwtAccessSecret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtAccessSecret)) // decode from Base64 to []Byte
        this.jwtRefreshSecret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtRefreshSecret)) // and Keys.hmacShaKeyFo for Key object
    }

    // TODO разобраться с датой
    fun generateAccessToken(client: Client): String{
        val currentDateTime = LocalDateTime.now() // take current dateTime
        val accessExpirationInstant: Instant = currentDateTime.plusMinutes(5).atZone(ZoneId.systemDefault()).toInstant() // Set expiration datetime + 5 minutes
        val accessException: Date = Date.from(accessExpirationInstant) // convert to data
        return Jwts.builder()
            .setSubject(client.phone) // set subject param
            .setExpiration(accessException) // set exp param
            .signWith(jwtAccessSecret) // sign token
            .claim("name", client.name) // set claim params
            .claim("status", client.status) // set claim params
            .compact()
    }
    fun generateRefreshToken(client: Client): String{
        val currentDateTime = LocalDateTime.now() // take current dateTime
        val refreshExpirationInstant: Instant = currentDateTime.plusDays(1).atZone(ZoneId.systemDefault()).toInstant() // Set expiration datetime + 1 day
        val refreshException: Date = Date.from(refreshExpirationInstant) // convert to data
        return Jwts.builder()
            .setSubject(client.phone) // set subject param
            .setExpiration(refreshException) // set exp param
            .signWith(jwtRefreshSecret) // sign token
            .compact()
    }

    fun validateAccessToken(@NonNull accessToken: String?): Boolean {
        return validateToken(accessToken!!, jwtAccessSecret)
    }

    fun validateRefreshToken(@NonNull refreshToken: String?): Boolean {
        log.info("jwtRefreshSecret $jwtRefreshSecret")
        log.info("token $refreshToken")
        return validateToken(refreshToken!!, jwtRefreshSecret)
    }

    private fun validateToken(token: String, secret: Key): Boolean {
        log.info("token")
        try {
            Jwts.parserBuilder() // create a JwtParserBuilder instance
                .setSigningKey(secret)// check sign
                .build() // return safe parser
                .parseClaimsJws(token) //Parses compact serialized JWS string and returns the resulting plaintext JWS instance.
            log.info("Signature ok")
            return true
        } catch (expEx: ExpiredJwtException) {
            log.error("Token expired", expEx)
        } catch (unsEx: UnsupportedJwtException) {
            log.error("Unsupported jwt", unsEx)
        } catch (mjEx: MalformedJwtException) {
            log.error("Malformed jwt", mjEx)
        } catch (sEx: io.jsonwebtoken.security.SignatureException) {
            log.error("Invalid signature", sEx)
        } catch (e: Exception) {
            log.error("invalid token", e)
        }
        return false
    }

    fun getAccessClaims(token: String): Claims? {
        return getClaims(token, jwtAccessSecret)
    }

    fun getRefreshClaims(token: String): Claims? {
        return getClaims(token, jwtRefreshSecret)
    }

    private fun getClaims(token: String, secret: Key): Claims? {
        return  Jwts.parserBuilder()
            .setSigningKey(secret)
            .build()
            .parseClaimsJws(token)
            .body
    }


}