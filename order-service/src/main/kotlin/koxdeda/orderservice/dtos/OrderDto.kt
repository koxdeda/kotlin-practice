package koxdeda.orderservice.dtos

import koxdeda.orderservice.dtos.enums.OrderStatus


data class OrderDto(
    val orderNumber: Int? = null,
    val totalCost: Int? = null,
    val status : OrderStatus? = null,
    val productSku: Int? = null,
    val productName: String? = null,
    val amount: Int? = null,
    val productPrice: Int? = null,
)
