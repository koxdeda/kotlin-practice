package com.example.clientprofile.controllers

import com.example.clientprofile.dtos.client.ClientCreateDto
import com.example.clientprofile.dtos.client.ClientCreateResponseDto
import com.example.clientprofile.dtos.client.ClientDto
import com.example.clientprofile.dtos.client.ClientUpdateDto
import com.example.clientprofile.service.ClientService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletResponse


@RestController
@RequestMapping("/clients")
class ClientProfileController(val clientService: ClientService) {
    private val log = LoggerFactory.getLogger(javaClass)

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    fun createClient(@RequestBody clientCreate: ClientCreateDto, response: HttpServletResponse): ClientCreateResponseDto {
        return clientService.createClient(clientCreate, response)
    }

    @GetMapping("/{clientId}")
    @ResponseStatus(HttpStatus.OK)
    fun getClientById(@PathVariable("clientId") clientId: Long): ClientDto {
        return clientService.getClient(clientId)
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    fun getAllClients(@RequestParam("offset") offset: Int, @RequestParam("limit") limit: Int): List<ClientDto> {
        return clientService.getAllClients(offset, limit)
    }

    @PutMapping("/{id}")
    fun updateClient(@PathVariable id: Long, @RequestBody dto: ClientUpdateDto): ClientCreateResponseDto {
        return  clientService.updateClient(id, dto)
    }


    @GetMapping(value = ["/get-cookie"])
    fun readCookie(@CookieValue(value = "data") data: String?): ResponseEntity<*>? {
        return ResponseEntity.ok().body(data)
    }





}