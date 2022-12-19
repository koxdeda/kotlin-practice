package koxdeda.orderservice.feign.dtos

data class ClientDto(

    var name: String? = null,
    var phone: String? = null,
    var email: String? = null,
    val status: Status? = null,
    val phonesChangeHistory: List<PhoneChangeHistoryDto>? = null
)

