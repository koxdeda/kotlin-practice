package com.example.productservice.service.impl



import com.example.productservice.dtos.ProductDto
import com.example.productservice.exception.ProductAlreadyExist
import com.example.productservice.exception.ProductNotFoundException
import com.example.productservice.repository.ProductRepository
import org.junit.Assert
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.junit4.SpringRunner


@RunWith(SpringRunner::class)
@SpringBootTest
@TestPropertySource("/application.properties")
@Sql(value = ["/delete-after-each-test.sql"], executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class ProductServiceImplTest {

    private val log = LoggerFactory.getLogger(javaClass)

    @Mock
    private val productRepository: ProductRepository = mock(ProductRepository::class.java)

    @Autowired
    val productService: ProductServiceImpl = ProductServiceImpl(productRepository)


    @Test
    fun `should create new product`() {
        val product = ProductDto(SKU)
        val createdProduct = productService.createProduct(product)
        Assert.assertNotNull(createdProduct)
        createdProduct.apply {
            Assert.assertEquals(SKU, sku)
            Assert.assertEquals(NAME, name)
            Assert.assertEquals(DESCRIPTION, description)
            Assert.assertEquals(PRICE, price)
            Assert.assertEquals(QUANTITY, quantity)
        }
    }

    @Test
    fun `should return an error when creating second product`() {
        val product = ProductDto(SKU)
        val product2 = ProductDto(SKU)
        productService.createProduct(product)

        val thrown: Throwable = assertThrows(ProductAlreadyExist::class.java) {
            productService.createProduct(product2)
        }
        log.info("Error type is - ${thrown.message}")
        Assert.assertEquals("BAD_REQUEST", thrown.message)
    }


    @Test
    fun `should return an updated product`() {
        val product = ProductDto(SKU)
        val createdProduct = productService.createProduct(product)

        val updatedProduct = productService.updateProduct(createdProduct.id!!, ProductDto(
            1111, "update test", "update test", 10, 10
        ))
        Assert.assertNotNull(updatedProduct)
        updatedProduct.apply {
            Assert.assertEquals(UPDATED_SKU, sku)
            Assert.assertEquals(UPDATED_NAME, name)
            Assert.assertEquals(UPDATED_DESCRIPTION, description)
            Assert.assertEquals(UPDATED_PRICE, price)
            Assert.assertEquals(UPDATED_QUANTITY, quantity)
        }
    }

    @Test
    fun `should return an error when updating product`() {

        val thrown: Throwable = assertThrows(ProductNotFoundException::class.java) {
            productService.updateProduct(1, ProductDto(
                1111, "update test", "update test", 10, 10
            ))
        }
        log.info("Error type is - ${thrown.message}")
        Assert.assertEquals("NOT_FOUND", thrown.message)

    }

    @Test
    fun `should update product quantity`() {
        val product = ProductDto(SKU, null, null, null, QUANTITY)
        val createdProduct = productService.createProduct(product)
        val updatedProduct = productService.updateProductQuantity(createdProduct.sku!!, UPDATE_QUANTITY_VALUE)
        Assert.assertNotNull(updatedProduct)
        updatedProduct.apply {
            Assert.assertEquals(SKU, sku)
            Assert.assertEquals(NAME, name)
            Assert.assertEquals(DESCRIPTION, description)
            Assert.assertEquals(PRICE, price)
            Assert.assertEquals(QUANTITY - UPDATE_QUANTITY_VALUE, quantity)
        }

    }

    @Test
    fun `should return an error, when update product quantity`() {
        val thrown: Throwable = assertThrows(ProductNotFoundException::class.java) {
            productService.updateProductQuantity(1, 10)
        }
        log.info("Error type is - ${thrown.message}")
        Assert.assertEquals("NOT_FOUND", thrown.message)
    }


    @Test
    fun `should return a product by given id`() {
        val product = ProductDto(SKU)
        val createdProduct = productService.createProduct(product)
        val existingProduct = productService.getProduct(createdProduct.id!!)
        Assert.assertNotNull(existingProduct)
        existingProduct.apply {
            Assert.assertEquals(createdProduct.sku, existingProduct.sku)
        }
    }

    @Test
    fun `should return an error when getting product by given id`() {

        val thrown: Throwable = assertThrows(ProductNotFoundException::class.java) {
            productService.getProduct(1)
        }
        Assert.assertEquals("NOT_FOUND", thrown.message)
    }

    @Test
    fun `should return true, product is in a stock`() {
        val product = ProductDto(SKU, null, null, null, 10)
        val createdProduct = productService.createProduct(product)
        val result = productService.isInStock(createdProduct.sku!!, 10)
        Assert.assertTrue(result)
    }

    @Test
    fun `should return false, product is not in a stock`() {
        val product = ProductDto(SKU, null, null, null, 9)
        val createdProduct = productService.createProduct(product)
        val result = productService.isInStock(createdProduct.sku!!, 10)
        Assert.assertFalse(result)
    }

    @Test
    fun `should return false, when checking product is in a stock, if product not found by sku`() {
        Assert.assertFalse(productService.isInStock(1, 10))
    }

    @Test
    fun `should return a list of products`() {
        productService.createProduct(ProductDto(SKU))
        productService.createProduct(ProductDto(2))
        productService.createProduct(ProductDto(3))
        val result = productService.getAllProducts(0, 2)

        Assert.assertEquals(2, result.size)
    }

    @Test
    fun `should return an emptyList`() {
        val result = productService.getAllProducts(0, 2)
        Assert.assertEquals(0, result.size)
    }


    companion object{
        const val SKU = 999999
        const val NAME = "test"
        const val DESCRIPTION = "test"
        const val PRICE = 100
        const val QUANTITY = 20
        const val UPDATED_SKU = 1111
        const val UPDATED_NAME = "update test"
        const val UPDATED_DESCRIPTION = "update test"
        const val UPDATED_PRICE = 10
        const val UPDATED_QUANTITY = 10
        const val UPDATE_QUANTITY_VALUE = 10
    }
}