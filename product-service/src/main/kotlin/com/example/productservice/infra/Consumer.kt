package com.example.productservice.infra


import com.example.productservice.dtos.OrderOutboxDto
import com.example.productservice.service.ProductService
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Component

@Component
class Consumer(val productService: ProductService) {
    private val log = LoggerFactory.getLogger(javaClass)

    @KafkaListener(topics = ["\${kafka.topics.order-service-outbox}"], groupId = "\${kafka.group.id}")
    fun listenGroupFoo(consumerRecord: ConsumerRecord<Any, Any>, ack: Acknowledgment) {
        log.info("Message received {}", consumerRecord)
        println("Message received $consumerRecord")
        println("Message value is: ${consumerRecord.value()}")

        val orderOutboxDto = consumerRecord.value() as OrderOutboxDto
        println("ID is - ${orderOutboxDto.id}")

        ack.acknowledge()


        orderOutboxDto.productSku?.let {
                log.info("Run update product")
            orderOutboxDto.amount?.let { it1 -> productService.updateProductQuantity(orderOutboxDto.productSku, it1) }
        }
    }
}
