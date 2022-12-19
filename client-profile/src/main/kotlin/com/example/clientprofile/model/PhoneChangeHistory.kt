package com.example.clientprofile.model

import lombok.AllArgsConstructor
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import javax.persistence.*





@Entity
@Table(name = "t_phone_change_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
data class PhoneChangeHistory(
    var oldValue: String? = null,
    var newValue: String? = null,
    var changedDate: String? = null,

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "client_id")
    private val client: Client? = null,
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null,
)