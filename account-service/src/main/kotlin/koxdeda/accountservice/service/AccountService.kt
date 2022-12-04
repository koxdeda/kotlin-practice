package koxdeda.accountservice.service

import koxdeda.accountservice.dtos.AccountDto
import koxdeda.accountservice.dtos.enums.CurrencyType
import koxdeda.accountservice.model.Account
import koxdeda.accountservice.model.CurrencyRecord


interface AccountService {
    fun createAccount(clientId: Long): Account
    fun getAccount(id: Long): AccountDto
    fun changeBalance(clientId: Long, currency: CurrencyType, amount: Double): AccountDto

    fun checkBalance(clientId: Long, cost: Double, currency: CurrencyType): Boolean


}

