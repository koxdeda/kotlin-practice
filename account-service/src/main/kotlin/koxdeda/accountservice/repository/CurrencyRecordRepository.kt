package koxdeda.accountservice.repository

import koxdeda.accountservice.model.CurrencyRecord
import org.springframework.data.jpa.repository.JpaRepository

interface CurrencyRecordRepository: JpaRepository<CurrencyRecord?, Long?> {
}