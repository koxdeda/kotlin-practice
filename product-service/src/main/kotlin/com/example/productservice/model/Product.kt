package com.example.productservice.model


import com.example.productservice.dtos.ProductDto
import lombok.AllArgsConstructor
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import javax.persistence.*



fun Product.toProductDto() = ProductDto(
    sku = sku,
    name = name,
    description = description,
    price = price,
    quantity = quantity,
)


@Entity
@Table(name = "t_products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
data class Product(

    val sku: Int? = null,
    val name: String? = null,
    val description: String? = null,
    val price: Int? = null,
    val quantity: Int? = null,
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null
)
