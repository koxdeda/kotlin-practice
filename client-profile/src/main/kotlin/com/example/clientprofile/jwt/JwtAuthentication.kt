package com.example.clientprofile.jwt

import com.example.clientprofile.dtos.enums.Role
import lombok.Getter
import lombok.Setter
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority


@Getter
@Setter
class JwtAuthentication : Authentication {
    private var authenticated = false
    private var username: String? = null
    private var roles: Set<Role>? = null

    override fun getAuthorities(): Collection<GrantedAuthority?>? {
        return roles!!
    }
    @Throws(IllegalArgumentException::class)
    fun setAuthorities(roles: Set<Role>): Set<Role> {
        return roles
    }

    override fun getCredentials(): Any? {
        return null
    }

    override fun getDetails(): Any? {
        return null
    }

    override fun getPrincipal(): Any? {
        return null
    }

    override fun isAuthenticated(): Boolean {
        return authenticated
    }

    @Throws(IllegalArgumentException::class)
    override fun setAuthenticated(isAuthenticated: Boolean) {
        authenticated = isAuthenticated
    }

    @Throws(IllegalArgumentException::class)
    fun setName(name: String) {
        username = name
    }

    override fun getName(): String {
        return username!!
    }
}