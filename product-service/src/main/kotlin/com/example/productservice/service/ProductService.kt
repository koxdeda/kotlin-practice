package com.example.productservice.service

import com.example.productservice.dtos.ProductDto

interface ProductService {

    fun createProduct(createProduct: ProductDto): ProductDto

    fun updateProduct(productId: Long, createProduct: ProductDto): ProductDto

    fun updateProductQuantity(sku: Int, quantity: Int): ProductDto

    fun getProduct(id: Long): ProductDto

    fun isInStock(sku: Int, amount: Int): Boolean

    fun getAllProducts(offset: Int, limit: Int): List<ProductDto>

}