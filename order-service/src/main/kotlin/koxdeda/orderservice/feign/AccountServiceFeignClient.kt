package koxdeda.orderservice.feign

import koxdeda.orderservice.dtos.enums.CurrencyType
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "account-service", path = "/account", url = "http://localhost:8082")
interface AccountServiceFeignClient {

    @GetMapping("/balance/check")
    fun checkBalance(@RequestParam("clientId") clientId: Long,
                     @RequestParam("cost") cost: Double,
                     @RequestParam("currency") currency: CurrencyType
    ) : Boolean
}