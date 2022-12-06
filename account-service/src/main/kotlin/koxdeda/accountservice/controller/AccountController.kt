package koxdeda.accountservice.controller

import koxdeda.accountservice.dtos.AccountDto
import koxdeda.accountservice.dtos.enums.CurrencyType
import koxdeda.accountservice.model.Account
import koxdeda.accountservice.model.CurrencyRecord
import koxdeda.accountservice.service.AccountService
import lombok.RequiredArgsConstructor
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
class AccountController(
    val accountService: AccountService)
{

    private val log = LoggerFactory.getLogger(javaClass)


    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getAccount(@PathVariable("id") id: Long): AccountDto {
        log.info("WTF")
        return accountService.getAccount(id)
    }

    @GetMapping("/balance/check")
    @ResponseStatus(HttpStatus.OK)
    fun checkBalance(
        @RequestParam("clientId") clientId: Long,
        @RequestParam("cost") cost: Double,
        @RequestParam("currency") currency: CurrencyType
    ): Boolean {
        return accountService.checkBalance(clientId, cost, currency)
    }



    @PutMapping("/balance/change/{clientId}")

    fun changeBalance(
        @PathVariable("clientId") clientId: Long,
        @RequestParam("amount") amount: Double,
        @RequestParam("currency") currency: CurrencyType
    ): AccountDto {
        return accountService.changeBalance(clientId, currency, amount)
    }

}