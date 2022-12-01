package com.example.clientprofile.dtos.client

import com.example.clientprofile.model.PhoneChangeHistory

fun PhoneChangeHistory.toPhoneChangeHistoryDto() = PhoneChangeHistoryDto(
    newValue = newValue,
    oldValue = oldValue,
    changedDate = changedDate,
)


data class PhoneChangeHistoryDto (
    val newValue: String? = null,
    val oldValue: String? = null,
    val changedDate: String? = null
)

