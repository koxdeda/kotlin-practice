package com.example.productservice.service

import com.example.productservice.dtos.ProductDto
import com.example.productservice.model.Product

interface ProductService {

    fun createProduct(createProduct: ProductDto): ProductDto

    fun updateProduct(productId: Long, createProduct: ProductDto): ProductDto

    fun getProduct(id: Long): ProductDto

    fun getAllProducts(offset: Int, limit: Int): List<ProductDto>

}