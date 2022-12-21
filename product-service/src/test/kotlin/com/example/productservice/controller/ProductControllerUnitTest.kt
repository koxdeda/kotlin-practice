package com.example.productservice.controller

import com.example.productservice.dtos.ProductDto
import com.example.productservice.service.ProductService
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.springframework.boot.test.context.SpringBootTest
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`


@SpringBootTest
@RunWith(MockitoJUnitRunner::class)
class ProductControllerUnitTest {

    @Mock
    private val productService: ProductService? = null
    @InjectMocks
    private lateinit var productController: ProductController

    @Test
    fun createProduct() {
        `when`(productService?.createProduct(ProductDto())).thenReturn(ProductDto())

        productController.createProduct( ProductDto())

        verify(productService)?.createProduct(ProductDto())
    }

    @Test
    fun updateProduct() {
        `when`(productService?.updateProduct(0, ProductDto())).thenReturn(ProductDto())

        productController.updateProduct(0, ProductDto())

        verify(productService)?.updateProduct(0, ProductDto())
    }

    @Test
    fun getProduct() {
        `when`(productService?.getProduct(0)).thenReturn(ProductDto())

        productController.getProduct(0)

        verify(productService)?.getProduct(0)
    }

    @Test
    fun isInStock(){
        `when`(productService?.isInStock(0, 5)).thenReturn(true)

        productController.isInStock(0, 5)

        verify(productService)?.isInStock(0, 5 )
    }

    @Test
    fun getAllProducts() {
        `when`(productService?.getAllProducts(0, 5)).thenReturn(emptyList())

        productController.getAllProducts(0, 5)

        verify(productService)?.getAllProducts(0, 5 )
    }


}