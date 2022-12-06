package koxdeda.orderservice.model

import koxdeda.orderservice.dtos.OrderDto
import koxdeda.orderservice.dtos.enums.OrderStatus
import lombok.AllArgsConstructor
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import javax.persistence.*

fun Order.toOrderDto() = OrderDto(
    totalCost = totalCost,
    status = status,
    orderNumber = orderNumber,
    amount = amount,
    productName = productName,
    productPrice = productPrice,
    productSku = productSku

)

@Entity
@Table(name = "t_orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
data class Order(

    val orderNumber: Int? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null,
    val totalCost: Int? = null,
    val status : OrderStatus? = null,
    val productSku: Int? = null,
    val productName: String? = null,
    val amount: Int? = null,
    val productPrice: Int? = null,
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null
)
