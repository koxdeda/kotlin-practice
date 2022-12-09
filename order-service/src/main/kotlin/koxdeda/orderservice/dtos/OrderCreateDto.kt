package koxdeda.orderservice.dtos

data class OrderCreateDto(
    val orderNumber: Int? = null,
    val totalCost: Int? = null,
    val productSku: Int? = null,
    val productName: String? = null,
    val amount: Int? = null,
    val productPrice: Int? = null,
    val clientId: Long? = null,
)

