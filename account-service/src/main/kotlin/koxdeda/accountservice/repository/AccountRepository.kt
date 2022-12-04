package koxdeda.accountservice.repository

import koxdeda.accountservice.model.Account
import org.springframework.data.jpa.repository.JpaRepository

interface AccountRepository: JpaRepository<Account, Long?> {

    fun findByClientId(clientId: Long): Account?

}