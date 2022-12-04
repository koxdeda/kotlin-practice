package koxdeda.accountservice.infra

import koxdeda.accountservice.dtos.ClientOutboxDto
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

    @KafkaListener(topics = ["\${kafka.topics.topic}"], groupId = "\${kafka.group.id}")
    fun listenGroupFoo(consumerRecord: ConsumerRecord<Any, Any>, ack: Acknowledgment) {
        log.info("Message received {}", consumerRecord)
        println("Message received $consumerRecord")
        println("Message value is: ${consumerRecord.value()}")

        val clientOutboxDto = consumerRecord.value() as ClientOutboxDto
        println("ID is - ${clientOutboxDto.id}")

        ack.acknowledge()

        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")

        clientOutboxDto.id?.let {
                log.info("Run create Account")
                accountService.createAccount(clientOutboxDto.id)
        }
    }
}
