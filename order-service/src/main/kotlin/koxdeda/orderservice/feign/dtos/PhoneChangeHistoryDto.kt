package koxdeda.orderservice.feign.dtos

data class PhoneChangeHistoryDto (
    val newValue: String? = null,
    val oldValue: String? = null,
    val changedDate: String? = null
)

