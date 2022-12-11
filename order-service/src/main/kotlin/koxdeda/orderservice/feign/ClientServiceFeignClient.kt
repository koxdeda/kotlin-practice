package koxdeda.orderservice.feign

import koxdeda.orderservice.feign.dtos.ClientDto
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestHeader


@FeignClient(name = "client-service", path = "/clients", url = "http://localhost:8081")
interface ClientServiceFeignClient {

    @GetMapping("/{clientId}")
    fun getClientById(@RequestHeader("Authorization") bearerToken: String, @PathVariable("clientId") clientId: Long): ClientDto?
}