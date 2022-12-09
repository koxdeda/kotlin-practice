package koxdeda.accountservice.dtos.serializer

import com.fasterxml.jackson.databind.ObjectMapper
import koxdeda.accountservice.dtos.AccountDto
import org.apache.kafka.common.errors.SerializationException
import org.apache.kafka.common.serialization.Serializer
import org.slf4j.LoggerFactory

class AccountSerializer: Serializer<AccountDto> {

        private val objectMapper = ObjectMapper()
        private val log = LoggerFactory.getLogger(javaClass)

        override fun serialize(topic: String?, data: AccountDto?): ByteArray? {
            log.info("Serializing...")
            return objectMapper.writeValueAsBytes(
                data ?: throw SerializationException("Error when serializing Product to ByteArray[]")
            )
        }

        override fun close() {}
}
