package koxdeda.orderservice.service.impl

import kotlinx.coroutines.*
import koxdeda.orderservice.dtos.OrderDto
import koxdeda.orderservice.dtos.OrderCreateDto
import koxdeda.orderservice.dtos.enums.CurrencyType
import koxdeda.orderservice.dtos.enums.OrderStatus
import koxdeda.orderservice.exception.ValidationException
import koxdeda.orderservice.feign.AccountServiceFeignClient
import koxdeda.orderservice.feign.ClientServiceFeignClient
import koxdeda.orderservice.feign.ProductServiceFeignClient
import koxdeda.orderservice.feign.dtos.ClientDto
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
    @Autowired
    private val accountServiceFeignClient: AccountServiceFeignClient,
    @Autowired
    private val clientServiceFeignClient: ClientServiceFeignClient,
    @Autowired
    private val productServiceFeignClient: ProductServiceFeignClient
): OrderService {
    private val log = LoggerFactory.getLogger(javaClass)
    val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")

    // TODO добавить корутины
    override suspend fun createOrder(bearerToken: String, createOrder: OrderCreateDto): OrderDto  {

        createOrder.apply {
            if (checkOrderParams(bearerToken, createOrder) == true) {
                log.info("Got response from coroutine")
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
            } else {
                throw ValidationException("")
            }
        }
    }

    suspend fun checkOrderParams(bearerToken: String, createOrder: OrderCreateDto) = coroutineScope {
        log.info("Go to checkParams")
        createOrder.apply {
            log.info("Go to check product")
            val isProductAvailable = async { productServiceFeignClient.getProduct(productSku!!, amount!!) }
            log.info("Go to check account")
            val balanceAvailable = async {
                accountServiceFeignClient.checkBalance(
                    clientId!!,
                    totalCost!!.toDouble(),
                    CurrencyType.RUR
                )
            }
            log.info("Go to check client")
            val isClientExists = async { clientServiceFeignClient.getClientById(bearerToken, clientId!!) }

            return@coroutineScope isValidOrder(isProductAvailable.await(), balanceAvailable.await(), isClientExists.await())

        }
    }
    suspend fun isValidOrder(p: Boolean, a: Boolean, c: ClientDto?) = runBlocking {
        return@runBlocking p && a && c != null
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