package com.example.productservice.repository

import com.example.productservice.model.Product
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository: JpaRepository<Product, Long?> {
    override fun findAll(pageable: Pageable): Page<Product>
}