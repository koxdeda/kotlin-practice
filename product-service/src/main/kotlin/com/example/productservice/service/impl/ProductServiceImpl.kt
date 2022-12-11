package com.example.productservice.service.impl

import com.example.productservice.dtos.ProductDto
import com.example.productservice.exception.ProductNotFoundException
import com.example.productservice.model.Product
import com.example.productservice.model.toProductDto
import com.example.productservice.repository.ProductRepository
import com.example.productservice.service.ProductService
import org.slf4j.LoggerFactory
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ProductServiceImpl(
    private val productRepository: ProductRepository
): ProductService {
    private val log = LoggerFactory.getLogger(javaClass)


    override fun createProduct(createProduct: ProductDto): ProductDto {
        createProduct.apply {
            val product = Product(
            sku,
            name,
            description,
            price,
            quantity
            )
            productRepository.save(product)
            return product.toProductDto()
        }
    }

    override fun updateProduct(productId: Long, createProduct: ProductDto): ProductDto {
        createProduct.apply {
            var product = productRepository.findByIdOrNull(productId) ?: throw ProductNotFoundException("id")
            product = Product(
                sku ?: product.sku,
                name ?: product.name,
                description ?: product.description,
                price ?: product.price,
                quantity ?: product.quantity,
                product.id
            )
            productRepository.save(product)

            return product.toProductDto()
        }
    }

    override fun updateProductQuantity(sku: Int, quantity: Int): ProductDto {
            var product = productRepository.getOneBySku(sku) ?: throw ProductNotFoundException("sku")
            product = Product(
                product.sku,
                product.name,
                product.description,
                product.price,
                product.quantity?.minus(quantity),
                product.id
            )
            productRepository.save(product)
            return product.toProductDto()
    }

    override fun getProduct(id: Long): ProductDto {
        return productRepository.findByIdOrNull(id)?.toProductDto() ?: throw ProductNotFoundException("id")
    }

    override fun isInStock(sku: Int, amount: Int): Boolean {
        val product = productRepository.getOneBySku(sku) ?: return false
        return product.quantity!! >= amount
    }

    override fun getAllProducts(offset: Int, limit: Int): List<ProductDto> {
        val pageIndex = offset/limit
        return productRepository.findAll(PageRequest.of(pageIndex ,limit)).map { it.toProductDto() }.toList()
    }

}