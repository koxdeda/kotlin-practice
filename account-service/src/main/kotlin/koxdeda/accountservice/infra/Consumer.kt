package koxdeda.accountservice.infra

import koxdeda.accountservice.dtos.ClientOutboxDto
import koxdeda.accountservice.dtos.OrderOutboxDto
import koxdeda.accountservice.dtos.enums.CurrencyType
import koxdeda.accountservice.service.AccountService
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Component
import java.text.SimpleDateFormat
import kotlin.math.log

@Component
class Consumer(val accountService: AccountService) {
    private val log = LoggerFactory.getLogger(javaClass)

    @KafkaListener(topics = ["\${kafka.topics.client-profile-outbox}"], groupId = "\${kafka.group.id}")
    fun listenGroupClientOutbox(consumerRecord: ConsumerRecord<Any, Any>, ack: Acknowledgment) {
        log.info("Message received {}", consumerRecord)
        println("Message received $consumerRecord")
        println("Message value is: ${consumerRecord.value()}")

        val clientOutboxDto = consumerRecord.value() as ClientOutboxDto
        println("ID is - ${clientOutboxDto.id}")

        ack.acknowledge()


        clientOutboxDto.id?.let {
                log.info("Run create Account")
                accountService.createAccount(clientOutboxDto.id)
        }
    }

    @KafkaListener(topics = ["\${kafka.topics.order-service-outbox}"], groupId = "\${kafka.group.id}")
    fun listenGroupOrderOutbox(consumerRecord: ConsumerRecord<Any, Any>, ack: Acknowledgment) {
        log.info("Message received {}", consumerRecord)
        println("Message received $consumerRecord")
        println("Message value is: ${consumerRecord.value()}")

        val orderOutboxDto = consumerRecord.value() as OrderOutboxDto
        println("ID is - ${orderOutboxDto.id}")

        ack.acknowledge()

        orderOutboxDto.id?.let {
            log.info("Run update Account")
            orderOutboxDto.clientId?.let { it1 -> accountService.changeBalance(it1, CurrencyType.RUR, -orderOutboxDto.totalCost!!.toDouble()) }
        }
    }
}
