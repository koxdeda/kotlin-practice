package com.example.productservice.controller

import com.example.productservice.service.ProductService

import org.junit.jupiter.api.Test

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc



@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
internal class ProductControllerTest {
    @Autowired
    lateinit var productController: ProductController

    @Autowired
    lateinit var productService: ProductService

    @Autowired
    lateinit var mockMvc: MockMvc


    @Test
    fun createProduct() {
    }

    @Test
    fun updateProduct() {
    }

    @Test
    fun getProduct() {
    }


    @Test
    fun getAllProducts() {
    }


}