package com.example.clientprofile.dtos.client

import com.example.clientprofile.dtos.enums.Status


data class ClientDto(

    var name: String? = null,
    var phone: String? = null,
    var email: String? = null,
    val status: Status? = null,
    val phonesChangeHistory: List<PhoneChangeHistoryDto>? = null
)

