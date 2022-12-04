package com.example.clientprofile.model

import com.example.clientprofile.dtos.*
import com.example.clientprofile.dtos.client.ClientCreateResponseDto
import com.example.clientprofile.dtos.client.ClientDto
import com.example.clientprofile.dtos.client.ClientOutboxDto
import com.example.clientprofile.dtos.client.toPhoneChangeHistoryDto
import com.example.clientprofile.dtos.enums.Role
import com.example.clientprofile.dtos.enums.Status
import lombok.AllArgsConstructor
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import javax.persistence.*

fun Client.toClientCreateResponseDto() = ClientCreateResponseDto(
    id = id,
    name = name,
    phone = phone,
    email = email,
    status = status
)



fun Client.toClientDto() = ClientDto(
    name = name,
    phone = phone,
    email = email,
    status = status,
    phonesChangeHistory = phonesChangeHistory.map { it.toPhoneChangeHistoryDto() }
)

fun Client.toClientOutboxDto() = ClientOutboxDto(
    id = id,
    name = name,
    phone = phone,
    email = email,
    status = status,
    secretHash = secretHash,
    salt = salt

)

@Entity
@Table(name = "t_clients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
data class Client(
    var name: String? = null,
    var phone: String? = null,
    var email: String? = null,
    var status: Status? = null,
    var secretHash: String? = null,
    var salt: String? = null,
    @OneToMany(mappedBy = "client")
    var phonesChangeHistory: List<PhoneChangeHistory> = emptyList(),
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,
    @ElementCollection(targetClass=Integer::class)
    val roles: Set<Role>? = null

    )
