package com.example.productservice.controller


import com.example.productservice.dtos.ProductDto
import com.google.gson.Gson
import org.junit.Test

import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringJUnit4ClassRunner::class)
@AutoConfigureMockMvc
@Sql(value = ["/create-product-before-each-test.sql"], executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = ["/delete-after-each-test.sql"], executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class ProductControllerItTest {
    @Autowired
    private val mockMvc: MockMvc? = null

    @Test
    fun `should success create a product`() {
        val gson = Gson()
        val product = ProductDto(1234)
        this.mockMvc?.perform(post("$PRODUCT_SERVICE_URL/create")
            .contentType("application/json")
            .content(gson.toJson(product))
        )
            ?.andDo(print())
            ?.andExpect(status().isCreated)
            ?.andExpect(content().json(gson.toJson(product)))
    }

    @Test
    fun `should fail when create a product`() {
        val gson = Gson()
        val product = ProductDto(123)
        this.mockMvc?.perform(post("$PRODUCT_SERVICE_URL/create")
            .contentType("application/json")
            .content(gson.toJson(product))
        )
            ?.andDo(print())
            ?.andExpect(status().isBadRequest)
    }

    @Test
    fun `should success when update product`() {
        val gson = Gson()
        val product = ProductDto(999, "update_test", "update_test", 999, 999)
        this.mockMvc?.perform(put("$PRODUCT_SERVICE_URL/update/{id}", 1)
            .contentType("application/json")
            .content(gson.toJson(product))
        )
            ?.andDo(print())
            ?.andExpect(status().isOk)
            ?.andExpect(content().json(gson.toJson(product)))
    }

    @Test
    fun `should fail when update a product`() {
        val gson = Gson()
        val product = ProductDto(999, "update_test", "update_test", 999, 999)
        this.mockMvc?.perform(put("$PRODUCT_SERVICE_URL/update/{id}", 2)
            .contentType("application/json")
            .content(gson.toJson(product))
        )
            ?.andDo(print())
            ?.andExpect(status().isNotFound)
    }

    @Test
    fun `should return a product`() {
        val gson = Gson()
        val productDto = ProductDto(123, "test", "test", 1000, 20, 1)
        this.mockMvc?.perform(get("$PRODUCT_SERVICE_URL/{id}", 1))
            ?.andDo(print())
            ?.andExpect(status().isOk)
            ?.andExpect(content().json(gson.toJson(productDto)))
    }

    @Test
    fun `should return an error while getting product`() {
        this.mockMvc?.perform(get("$PRODUCT_SERVICE_URL/{id}", 2))
            ?.andDo(print())
            ?.andExpect(status().isNotFound)
    }

    @Test
    fun `should return true, product is in a stock`() {
        this.mockMvc?.perform(get("$PRODUCT_SERVICE_URL/available")
            .queryParam("sku", "123")
            .queryParam("amount", "10")
        )
            ?.andDo(print())
            ?.andExpect(status().isOk)
            ?.andExpect(content().string("true"))
    }

    @Test
    fun `should return false, product not found`() {
        this.mockMvc?.perform(get("$PRODUCT_SERVICE_URL/available")
            .queryParam("sku", "12")
            .queryParam("amount", "10")
        )
            ?.andDo(print())
            ?.andExpect(status().isOk)
            ?.andExpect(content().string("false"))
    }

    @Test
    fun `should return false, not enough product quantity for order`() {
        this.mockMvc?.perform(get("$PRODUCT_SERVICE_URL/available")
            .queryParam("sku", "123")
            .queryParam("amount", "100")
        )
            ?.andDo(print())
            ?.andExpect(status().isOk)
            ?.andExpect(content().string("false"))
    }

    @Test
    fun getAllProducts() {
        this.mockMvc?.perform(get(PRODUCT_SERVICE_URL)
            .queryParam("offset", "0")
            .queryParam("limit", "1")
        )
            ?.andDo(print())
            ?.andExpect(status().isOk)
    }

    companion object{
        const val PRODUCT_SERVICE_URL: String = "http://localhost:8083/products"
    }
}