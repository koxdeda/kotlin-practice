package koxdeda.orderservice.service

import koxdeda.orderservice.dtos.OrderDto
import koxdeda.orderservice.dtos.OrderCreateDto


interface OrderService {

    suspend fun createOrder(bearerToken: String, createOrder: OrderCreateDto): OrderDto
}