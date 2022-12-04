package koxdeda.accountservice.repository

import koxdeda.accountservice.dtos.enums.CurrencyType
import koxdeda.accountservice.model.Account
import koxdeda.accountservice.model.CurrencyRecord
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface CurrencyRecordRepository: JpaRepository<CurrencyRecord, Long?> {

    fun findByAccount(account: Account): CurrencyRecord?
}