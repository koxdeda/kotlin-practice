package com.example.clientprofile.service.impl

import at.favre.lib.crypto.bcrypt.BCrypt
import com.example.clientprofile.dtos.password.HashPasswordResponse
import com.example.clientprofile.service.PasswordService
import org.springframework.stereotype.Service
import java.nio.charset.StandardCharsets
import java.util.*
import javax.xml.bind.DatatypeConverter

@Service
class PasswordServiceImpl: PasswordService {

    override fun hashPassword(password: String): HashPasswordResponse {

        val salt = ByteArray(16)
        Random().nextBytes(salt)

        val hashData = BCrypt.withDefaults().hashRaw(6, salt, password.toByteArray(StandardCharsets.UTF_8)  )
        //HashData{cost=6, version=$2a$, rawSalt=55a4f8819a0d1528a8a0aa5998161ff7, rawHash=979c8ce63dceb60e9a5f5392949381ac9e3c954582d494}

        return HashPasswordResponse(
            DatatypeConverter.printHexBinary(hashData.rawHash),
            DatatypeConverter.printHexBinary(hashData.rawSalt)
        )
    }


    override fun checkPassword(salt: String, hashPassword: String, password: String): Boolean{

        val givenHash = BCrypt.withDefaults().hashRaw(
            6,
            salt.decodeHex(),
            password.toByteArray(StandardCharsets.UTF_8))


        return hashPassword == DatatypeConverter.printHexBinary(givenHash.rawHash)
    }

    fun String.decodeHex(): ByteArray {
        check(length % 2 == 0) { "Must have an even length" }

        return chunked(2)
            .map { it.toInt(16).toByte() }
            .toByteArray()
    }
}