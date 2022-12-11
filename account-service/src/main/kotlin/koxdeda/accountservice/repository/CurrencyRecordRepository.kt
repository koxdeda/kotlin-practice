package koxdeda.accountservice.repository

import koxdeda.accountservice.model.Account
import koxdeda.accountservice.model.CurrencyRecord
import org.hibernate.type.CurrencyType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface CurrencyRecordRepository: JpaRepository<CurrencyRecord, Long?> {
    @Query(value="SELECT * FROM `account-service`.t_currency c WHERE c.account_id=?1 AND c.currency=?2", nativeQuery = true)
    fun findByAccount(account: Account, currency: String): CurrencyRecord?
}