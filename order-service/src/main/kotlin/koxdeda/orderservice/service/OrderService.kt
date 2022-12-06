package koxdeda.orderservice.service

import koxdeda.orderservice.dtos.OrderDto
import koxdeda.orderservice.dtos.enums.OrderCreateDto


interface OrderService {

    fun createOrder(createOrder: OrderCreateDto): OrderDto

}