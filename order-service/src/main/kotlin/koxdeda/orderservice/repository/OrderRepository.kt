package koxdeda.orderservice.repository

import koxdeda.orderservice.model.Order
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface OrderRepository: JpaRepository<Order, Long?> {
    override fun findAll(pageable: Pageable): Page<Order>
}