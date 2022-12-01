package koxdeda.accountservice.model

import lombok.AllArgsConstructor
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import javax.persistence.*


@Entity
@Table(name = "t_accounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
data class Account(
    var clientId: Long? = null,
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null
)