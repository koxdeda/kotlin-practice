package koxdeda.orderservice.service.impl

import koxdeda.orderservice.dtos.OrderDto
import koxdeda.orderservice.dtos.enums.OrderCreateDto
import koxdeda.orderservice.dtos.enums.OrderStatus
import koxdeda.orderservice.model.Order
import koxdeda.orderservice.model.toOrderDto
import koxdeda.orderservice.repository.OrderRepository
import koxdeda.orderservice.service.OrderService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.text.SimpleDateFormat
import java.util.*

@Service
@Transactional
class OrderServiceImpl(
    private val productRepository: OrderRepository
): OrderService {
    private val log = LoggerFactory.getLogger(javaClass)
    val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")

    override fun createOrder(createOrder: OrderCreateDto): OrderDto {
        createOrder.apply {
            val order = Order(
                orderNumber,
                sdf.format(Date()),
                sdf.format(Date()),
                totalCost,
                OrderStatus.CANCELLED,
                productSku,
                productName,
                amount,
                productPrice
            )
            productRepository.save(order)
            return order.toOrderDto()
        }
    }

}