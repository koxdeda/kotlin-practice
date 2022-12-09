package koxdeda.orderservice.service

import koxdeda.orderservice.dtos.OrderDto
import koxdeda.orderservice.dtos.OrderCreateDto


interface OrderService {

    fun createOrder(createOrder: OrderCreateDto): OrderDto

}