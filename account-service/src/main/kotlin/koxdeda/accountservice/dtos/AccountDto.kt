package koxdeda.accountservice.dtos


data class AccountDto(
    val id: Long? = null,
    val balance: Int? = null,
    val clientId: Long? = null,
    val currencies: List<CurrencyRecordDto>? = null
)
