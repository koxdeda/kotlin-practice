package koxdeda.accountservice.controller

import koxdeda.accountservice.dtos.AccountDto
import koxdeda.accountservice.dtos.enums.CurrencyType
import koxdeda.accountservice.service.AccountService
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import org.springframework.boot.test.context.SpringBootTest


@SpringBootTest
@RunWith(MockitoJUnitRunner::class)
class AccountControllerUnitTest {

    @Mock
    private val accountService: AccountService? = null
    @InjectMocks
    private lateinit var accountController: AccountController

    @Test
    fun getAccount() {
        Mockito.`when`(accountService?.getAccount(0)).thenReturn(AccountDto())

        accountController.getAccount(0)

        Mockito.verify(accountService)?.getAccount(0)
    }

    @Test
    fun checkBalance() {
        Mockito.`when`(accountService?.checkBalance(0, 10.0, CurrencyType.RUR)).thenReturn(true)

        accountController.checkBalance(0, 10.0, CurrencyType.RUR)

        Mockito.verify(accountService)?.checkBalance(0, 10.0, CurrencyType.RUR)
    }

    @Test
    fun changeBalance() {
        Mockito.`when`(accountService?.changeBalance(0, CurrencyType.RUR, 10.0)).thenReturn(AccountDto())

        accountController.changeBalance(0, 10.0, CurrencyType.RUR)

        Mockito.verify(accountService)?.changeBalance(0, CurrencyType.RUR, 10.0)
    }


}