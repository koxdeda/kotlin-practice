package koxdeda.accountservice.service.impl

import koxdeda.accountservice.dtos.enums.CurrencyType
import koxdeda.accountservice.exception.AccountNotFoundException
import koxdeda.accountservice.exception.CurrencyNotFoundException
import koxdeda.accountservice.repository.AccountRepository
import koxdeda.accountservice.repository.CurrencyRecordRepository
import org.junit.Assert
import org.junit.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.transaction.annotation.Transactional

@Suppress("UNCHECKED_CAST")
@RunWith(SpringRunner::class)
@SpringBootTest
@TestPropertySource("/application.properties")
@Sql(value = ["/create-account-before-each-test.sql"], executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = ["/delete-after-each-test.sql"], executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Transactional
 class AccountServiceTest {

    @Mock
    private val accountRepository: AccountRepository = Mockito.mock(AccountRepository::class.java)

    @Mock
    private val currencyRecordRepository: CurrencyRecordRepository = Mockito.mock(CurrencyRecordRepository::class.java)

    @Autowired
    private val kafkaTemplate: KafkaTemplate<String, Any> = Mockito.mock(KafkaTemplate::class.java) as KafkaTemplate<String, Any>

    @Autowired
    val accountService: AccountServiceImpl = AccountServiceImpl(accountRepository, currencyRecordRepository, "", kafkaTemplate)


    // TODO Почему-то смотрит не на тестовую БД

    @Test
    fun `should return account by a given clientId`() {
        val existingAccount = accountService.getAccount(226)
        Assert.assertNotNull(existingAccount)
    }

    @Test
    fun `should return an error by a given clientId(not found)`() {
        val thrown: Throwable = assertThrows(AccountNotFoundException::class.java) {
            accountService.getAccount(0)
        }
        Assert.assertEquals("NOT_FOUND", thrown.message)
    }

    @Test
    fun `should return true, when checking balance`() {
        Assert.assertTrue(accountService.checkBalance(226, 20.0, CurrencyType.RUR))
    }

    @Test
    fun `should return false, when checking balance`() {
        Assert.assertFalse(accountService.checkBalance(226, 40.0, CurrencyType.RUR))
    }

    @Test
    fun `should return ann error Account not found, when checking balance`() {
        val thrown: Throwable = assertThrows(AccountNotFoundException::class.java) {
            accountService.checkBalance(0, 20.0, CurrencyType.RUR)
        }
        Assert.assertEquals("NOT_FOUND", thrown.message)
    }

    @Test
    fun `should return ann error not found, when checking balance`() {
        val thrown: Throwable = assertThrows(CurrencyNotFoundException::class.java) {
            accountService.checkBalance(226, 20.0, CurrencyType.ETH)
        }
        Assert.assertEquals("NOT_FOUND", thrown.message)
    }
}