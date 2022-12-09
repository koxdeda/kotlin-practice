package koxdeda.accountservice.dtos

import koxdeda.accountservice.dtos.enums.OrderStatus



data class OrderOutboxDto(
    val orderNumber: Int? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null,
    val totalCost: Int? = null,
    val status : OrderStatus? = null,
    val productSku: Int? = null,
    val productName: String? = null,
    val amount: Int? = null,
    val productPrice: Int? = null,
    val clientId: Long? = null,
    val id: Long? = null,
)
