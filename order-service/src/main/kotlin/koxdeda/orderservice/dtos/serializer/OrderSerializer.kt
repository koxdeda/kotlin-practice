package koxdeda.orderservice.dtos.serializer

import com.fasterxml.jackson.databind.ObjectMapper
import koxdeda.orderservice.model.Order
import org.apache.kafka.common.errors.SerializationException
import org.apache.kafka.common.serialization.Serializer
import org.slf4j.LoggerFactory

class OrderSerializer: Serializer<Order> {

        private val objectMapper = ObjectMapper()
        private val log = LoggerFactory.getLogger(javaClass)

        override fun serialize(topic: String?, data: Order?): ByteArray? {
            log.info("Serializing...")
            return objectMapper.writeValueAsBytes(
                data ?: throw SerializationException("Error when serializing Product to ByteArray[]")
            )
        }

        override fun close() {}
}
