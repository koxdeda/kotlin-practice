package koxdeda.accountservice.dtos


data class ClientOutboxDto (
    val id: Long? = null,
    var name: String? = null,
    var phone: String? = null,
    var email: String? = null,
    var status: String? = null,
    var secretHash: String? = null,
    var salt: String? = null,

    )
