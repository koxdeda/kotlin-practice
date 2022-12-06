package com.example.productservice.controller

import com.example.productservice.dtos.ProductDto
import com.example.productservice.service.ProductService
import lombok.RequiredArgsConstructor
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
class ProductController(
    val productService: ProductService
) {
    private val log = LoggerFactory.getLogger(javaClass)


    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    fun createProduct(@RequestBody createProduct: ProductDto): ProductDto {
        return productService.createProduct(createProduct)
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun updateProduct(
        @PathVariable("id") productId: Long,
        @RequestBody updateProduct: ProductDto
    ): ProductDto {
        return productService.updateProduct(productId, updateProduct)
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getProduct(@PathVariable("id") productId: Long): ProductDto {
        return productService.getProduct(productId)
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    fun getAllProducts(
        @RequestParam("offset") offset: Int,
        @RequestParam("limit") limit: Int
    ): List<ProductDto> {
        return productService.getAllProducts(offset, limit)
    }

}