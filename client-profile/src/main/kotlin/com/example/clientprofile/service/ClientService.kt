package com.example.clientprofile.service

import com.example.clientprofile.dtos.client.ClientCreateDto
import com.example.clientprofile.dtos.client.ClientCreateResponseDto
import com.example.clientprofile.dtos.client.ClientDto
import com.example.clientprofile.dtos.client.ClientUpdateDto
import javax.servlet.http.HttpServletResponse


interface ClientService {

    fun createClient(clientCreate: ClientCreateDto, response: HttpServletResponse): ClientCreateResponseDto
    fun getClient(id: Long): ClientDto
    fun updateClient(id: Long, dto: ClientUpdateDto): ClientCreateResponseDto
    fun getAllClients(offset: Int, limit: Int): List<ClientDto>
}