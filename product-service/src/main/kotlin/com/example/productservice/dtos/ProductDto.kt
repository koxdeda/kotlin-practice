package com.example.productservice.dtos



data class ProductDto(

    val sku: Int? = null,
    val name: String? = null,
    val description: String? = null,
    val price: Int? = null,
    val quantity: Int? = null,
    val id: Long? = null
)