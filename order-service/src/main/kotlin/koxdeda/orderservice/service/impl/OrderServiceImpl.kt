package koxdeda.orderservice.service.impl

import koxdeda.orderservice.dtos.OrderDto
import koxdeda.orderservice.dtos.OrderCreateDto
import koxdeda.orderservice.dtos.enums.OrderStatus
import koxdeda.orderservice.model.Order
import koxdeda.orderservice.model.toOrderDto
import koxdeda.orderservice.repository.OrderRepository
import koxdeda.orderservice.service.OrderService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.Message
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.text.SimpleDateFormat
import java.util.*

@Service
@Transactional
class OrderServiceImpl(
    private val productRepository: OrderRepository,
    @Value("\${kafka.topics.order-service-outbox}") val topic: String,
    @Autowired
    private val kafkaTemplate: KafkaTemplate<String, Any>,
): OrderService {
    private val log = LoggerFactory.getLogger(javaClass)
    val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")

    // TODO Добавить проверку наличия товара (в product-service) и доступных средств (в account-service)
    override fun createOrder(createOrder: OrderCreateDto): OrderDto {
        createOrder.apply {
            val order = Order(
                orderNumber,
                sdf.format(Date()),
                sdf.format(Date()),
                totalCost,
                OrderStatus.CREATED,
                productSku,
                productName,
                amount,
                productPrice,
                clientId
            )
            productRepository.save(order)

            sendOrderOutbox(order)

            return order.toOrderDto()
        }
    }

    fun sendOrderOutbox(message: Order) {
        try {
            log.info("Sending message to Kafka {}", message)
            val sendingMessage: Message<Order> = MessageBuilder
                .withPayload(message)
                .setHeader(KafkaHeaders.TOPIC, topic)
                .setHeader("X-Custom-Header", "Custom header here")
                .build()
            kafkaTemplate.send(sendingMessage)
            log.info("Message sent with success")
        } catch (e: Exception) {
            log.error("Exception: $e")
        }
    }

}