package koxdeda.accountservice.model

import koxdeda.accountservice.dtos.enums.CurrencyType
import lombok.AllArgsConstructor
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import javax.persistence.*


@Entity
@Table(name = "t_currency")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
data class CurrencyRecord(
    var createdAt: String? = null,
    var updatedAt: String? = null,
    var currency: String? = null,
    var amount: Double? = null,
    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "account_id")
    private val account: Account? = null,
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null
)