package com.example.clientprofile.dtos.enums

import lombok.RequiredArgsConstructor
import org.springframework.security.core.GrantedAuthority

@RequiredArgsConstructor
enum class Role : GrantedAuthority {
    ADMIN, USER;

    private val vale: String? = null
    override fun getAuthority(): String {
        return vale!!
    }
}