package koxdeda.accountservice.dtos.deserializer

import com.fasterxml.jackson.databind.ObjectMapper
import koxdeda.accountservice.dtos.ClientOutboxDto
import org.apache.kafka.common.errors.SerializationException
import org.apache.kafka.common.serialization.Deserializer
import org.slf4j.LoggerFactory
import kotlin.text.Charsets.UTF_8

class ClientDeserializer : Deserializer<ClientOutboxDto> {
    private val objectMapper = ObjectMapper()
    private val log = LoggerFactory.getLogger(javaClass)

    override fun deserialize(topic: String?, data: ByteArray?): ClientOutboxDto? {
        log.info("Deserializing...")
        return objectMapper.readValue(
            String(
                data ?: throw SerializationException("Error when deserializing byte[] to Product"), UTF_8
            ), ClientOutboxDto::class.java
        )
    }

    override fun close() {}

}