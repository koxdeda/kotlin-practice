package koxdeda.orderservice.controller

import koxdeda.orderservice.dtos.OrderDto
import koxdeda.orderservice.dtos.OrderCreateDto
import koxdeda.orderservice.service.OrderService
import lombok.RequiredArgsConstructor
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
class OrderController(
    val orderService: OrderService
) {
    private val log = LoggerFactory.getLogger(javaClass)


    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    fun createOrder(@RequestHeader("Authorization") bearerToken: String, @RequestBody createOrder: OrderCreateDto): OrderDto {
        return orderService.createOrder(bearerToken, createOrder)
    }



}