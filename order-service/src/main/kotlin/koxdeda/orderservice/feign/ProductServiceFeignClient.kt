package koxdeda.orderservice.feign

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam


@FeignClient(name = "product-service", path = "/products", url = "http://localhost:8083")
interface ProductServiceFeignClient {

    @GetMapping("/available")
    suspend fun getProduct(@RequestParam("sku") sku: Int,@RequestParam("amount") amount: Int): Boolean

}