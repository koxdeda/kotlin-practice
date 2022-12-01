package koxdeda.accountservice.infra

import koxdeda.accountservice.dtos.ClientOutboxDto
import koxdeda.accountservice.model.Account
import koxdeda.accountservice.model.Currency
import koxdeda.accountservice.model.CurrencyRecord
import koxdeda.accountservice.repository.AccountRepository
import koxdeda.accountservice.repository.CurrencyRecordRepository
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Component
import java.text.SimpleDateFormat
import java.util.*

@Component
class Consumer(private val accountRepository: AccountRepository,
            private val currencyRecordRepository: CurrencyRecordRepository) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @KafkaListener(topics = ["\${kafka.topics.client}"], groupId = "\${kafka.group.id}")
    fun listenGroupFoo(consumerRecord: ConsumerRecord<Any, Any>, ack: Acknowledgment) {
        logger.info("Message received {}", consumerRecord)
        println("Message received $consumerRecord")
        println("Message value is: ${consumerRecord.value()}")

        val clientOutboxDto = consumerRecord.value() as ClientOutboxDto
        println("ID is - ${clientOutboxDto.id}")

        ack.acknowledge()

        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")

        clientOutboxDto.id?.let {
            val isAccountExist = accountRepository.findByClientId(it)

            if (isAccountExist.isEmpty()){
                val account = Account(clientOutboxDto.id)
                val currencyRecord = CurrencyRecord(
                    sdf.format(Date()),
                    sdf.format(Date()),
                    Currency.RUR.toString(),
                    0.0,
                    account
                )
                accountRepository.save(account)
                currencyRecordRepository.save(currencyRecord)
            }
        }
    }
}