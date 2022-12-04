package com.example.clientprofile.service.impl

import com.example.clientprofile.dtos.client.*
import com.example.clientprofile.dtos.enums.Status
import com.example.clientprofile.exception.*
import com.example.clientprofile.model.*
import com.example.clientprofile.repository.ClientRepository
import com.example.clientprofile.repository.PhoneChangeHistoryRepository
import com.example.clientprofile.service.ClientService
import com.example.clientprofile.service.PasswordService
import com.example.clientprofile.validators.RequestValidator
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.Message
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.text.SimpleDateFormat
import java.util.*
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse


@Service
@Transactional
class ClientServiceImpl(val clientRepository: ClientRepository,
                        val phoneChangeHistoryRepository: PhoneChangeHistoryRepository,
                        @Value("\${kafka.topics.topic}") val topic: String,
                        @Autowired
                    private val kafkaTemplate: KafkaTemplate<String, Any>,
                        val clientCreateValidator: RequestValidator,
                        val passwordService: PasswordService
): ClientService {


    private val log = LoggerFactory.getLogger(javaClass)
    val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")


    override fun createClient(clientCreate: ClientCreateDto, response: HttpServletResponse): ClientCreateResponseDto {

        if(clientCreateValidator.validClientCreate(clientCreate).isEmpty()){
            if (clientRepository.findAllByPhone(clientCreate.phone!!).isNotEmpty()){
                return ClientCreateResponseDto().apply {
                    success = false
                    resultCode = 400
                    resulDescription = "PHONE_ALREADY_EXIST"
                }
            }


            val resultHashPassword = passwordService.hashPassword(clientCreate.password!!)

            val client = Client(clientCreate.name,
                clientCreate.phone,
                clientCreate.email,
                Status.NEW,
                resultHashPassword.hash,
                resultHashPassword.salt,
            )
            clientRepository.save(client)



            val phoneChangeHistoryRecord = PhoneChangeHistory("", clientCreate.phone, sdf.format(Date()), client)
            phoneChangeHistoryRepository.save(phoneChangeHistoryRecord)


            val cookie = Cookie("Application.Token", "")
            cookie.path = "/"
            cookie.maxAge = 1800
            response.addCookie(cookie)
            response.contentType = "application/json"

        try {
            log.info("Saved a client to database")
            log.info("Sending message to Kafka {}", client.toClientOutboxDto())
            val message: Message<ClientOutboxDto> = MessageBuilder
                .withPayload(client.toClientOutboxDto())
                .setHeader(KafkaHeaders.TOPIC, topic)
                .setHeader("X-Custom-Header", "Custom header here")
                .build()
            kafkaTemplate.send(message)
            log.info("Message sent with success")
        } catch (e: Exception) {
            log.error("Exception: $e")
        }




            return client.toClientCreateResponseDto().apply {
                success = true
                resultCode = 201
                resulDescription = "CLIENT_CREATED"
            }
        }else throw ValidationError(clientCreateValidator.validClientCreate(clientCreate))


    }

    override fun getClient(id: Long): ClientDto {
        log.info("I am in service")
        return clientRepository.findByIdOrNull(id)
            ?.toClientDto()
            ?: throw ClientNotFoundException("id")
    }

    override fun updateClient(id: Long, dto: ClientUpdateDto): ClientCreateResponseDto {


        val client = clientRepository.findByIdOrNull(id) ?: throw ClientNotFoundException("id")

        if (dto.phone.let { clientRepository.findAllByPhone(it).isNotEmpty() })
            throw PhoneAlreadyUseException("phone")

        val oldNumber = client.phone

        client.name = dto.name
        client.phone = dto.phone
        client.email = dto.email
        clientRepository.save(client)

        val phoneChangeHistory = PhoneChangeHistory(oldNumber, client.phone, sdf.format(Date()), client)
        phoneChangeHistoryRepository.save(phoneChangeHistory)


        return client.toClientCreateResponseDto()
    }

    override fun getAllClients(offset: Int, limit: Int): List<ClientDto> {
        val pageIndex = offset/limit
        return clientRepository.findAll(PageRequest.of(pageIndex ,limit)).map {
            it.toClientDto()
        }.toList()
    }


}


