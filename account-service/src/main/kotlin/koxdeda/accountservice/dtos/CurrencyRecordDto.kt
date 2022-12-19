package koxdeda.accountservice.dtos

import koxdeda.accountservice.model.CurrencyRecord

fun CurrencyRecord.toCurrencyRecordDto() = CurrencyRecordDto(
    id = id,
    createdAt = createdAt,
    updatedAt = updatedAt,
    currency = currency.toString(),
    amount = amount

)


data class CurrencyRecordDto(
    val id: Long? = null,
    var createdAt: String? = null,
    var updatedAt: String? = null,
    var currency: String? = null,
    var amount: Double? = null,
)
