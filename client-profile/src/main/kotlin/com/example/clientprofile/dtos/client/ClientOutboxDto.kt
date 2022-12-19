package com.example.clientprofile.dtos.client

import com.example.clientprofile.dtos.enums.Status

data class ClientOutboxDto(
    val id: Long? = null,
    var name: String? = null,
    var phone: String? = null,
    var email: String? = null,
    var status: Status? = null,
    var secretHash: String? = null,
    var salt: String? = null,
)
