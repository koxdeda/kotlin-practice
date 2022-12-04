package koxdeda.accountservice.model

import koxdeda.accountservice.dtos.AccountDto
import koxdeda.accountservice.dtos.toCurrencyRecordDto
import lombok.AllArgsConstructor
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import javax.persistence.*


fun Account.toAccountDto() = AccountDto(
    id = id,
    balance = balance,
    clientId = clientId,
    currencies = currencies?.map { it.toCurrencyRecordDto() }
)

@Entity
@Table(name = "t_accounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
data class Account(
    var clientId: Long? = null,
    var balance: Int? = null,
    @OneToMany(mappedBy = "account")
    var currencies: List<CurrencyRecord>? = emptyList(),
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null,
)