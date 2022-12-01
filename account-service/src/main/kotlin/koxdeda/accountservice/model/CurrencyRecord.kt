package koxdeda.accountservice.model

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
    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.MERGE])
    @JoinColumn(name = "account_id")
    var account: Account? = null,
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null
)