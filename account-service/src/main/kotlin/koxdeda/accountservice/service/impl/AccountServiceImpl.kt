package koxdeda.accountservice.service.impl

import koxdeda.accountservice.dtos.AccountDto
import koxdeda.accountservice.dtos.enums.CurrencyType
import koxdeda.accountservice.exception.AccountAlreadyExistException
import koxdeda.accountservice.exception.AccountNotFoundException
import koxdeda.accountservice.model.Account
import koxdeda.accountservice.model.CurrencyRecord
import koxdeda.accountservice.model.toAccountDto
import koxdeda.accountservice.repository.AccountRepository
import koxdeda.accountservice.repository.CurrencyRecordRepository
import koxdeda.accountservice.service.AccountService
import org.slf4j.LoggerFactory
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.stereotype.Service
import java.text.SimpleDateFormat
import java.util.*

@Service
class AccountServiceImpl(
    private val accountRepository: AccountRepository,
    private val currencyRecordRepository: CurrencyRecordRepository,
): AccountService {

    private val log = LoggerFactory.getLogger(javaClass)
    private val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")

    // listen client-service outbox. works correct
    override fun createAccount(clientId: Long): Account{
        val isAccountExist = accountRepository.findByClientId(clientId)
        if (isAccountExist == null){
            val account = Account(clientId, 0)
            accountRepository.save(account)
            val currencyRecord = createCurrency(CurrencyType.RUR, 0.0, account)
            currencyRecordRepository.save(currencyRecord)
            return account
        }else {
            throw AccountAlreadyExistException("account")
        }
    }

    // works correct
    override fun getAccount(id: Long): AccountDto {
        log.info("Finding account")
        return accountRepository.findByClientId(id)?.toAccountDto() ?: throw AccountNotFoundException("clientId")
    }

    // TODO как-то правильно считать общий balance
    override fun changeBalance(clientId: Long, currency: CurrencyType, amount: Double): AccountDto{
        val account = accountRepository.findByClientId(clientId)
        return if (account != null){
            var currencyRecord = account.currencies!!.find { it.currency == currency.toString() }
            if (currencyRecord != null){
                currencyRecord.amount = currencyRecord.amount?.plus(amount)
                currencyRecord.updatedAt = sdf.format(Date())
                currencyRecordRepository.save(currencyRecord)

                account.balance = account.currencies!!.sumOf { it.amount!! }.toInt()
                accountRepository.save(account)
            }else {
                currencyRecord = createCurrency(currency, amount, account)
                currencyRecordRepository.save(currencyRecord)
            }

            accountRepository.findByClientId(clientId)!!.toAccountDto()
        }else {
            throw AccountNotFoundException("clientId")
        }
    }

    // works correct
    override fun checkBalance(clientId: Long, cost: Double, currency: CurrencyType): Boolean {
        val account = accountRepository.findByClientId(clientId)
        if (account != null) {
            val currencyRecord = account.let { currencyRecordRepository.findByAccount(it) }
            if (currencyRecord != null) {
                return currencyRecord.amount!! >= cost
            } else {
                throw NotFoundException()
            }
        }else {
            throw AccountNotFoundException("id")
        }
    }

    private fun createCurrency(currency: CurrencyType, amount: Double, account: Account): CurrencyRecord{
        return CurrencyRecord(
        sdf.format(Date()),
        sdf.format(Date()),
        currency.toString(),
        amount,
        account
        )
    }

}