package koxdeda.accountservice.controller

import com.google.gson.Gson
import koxdeda.accountservice.dtos.AccountDto
import org.junit.Test

import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringJUnit4ClassRunner::class)
@AutoConfigureMockMvc
@Sql(value = ["/create-account-before-each-test.sql"], executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = ["/delete-after-each-test.sql"], executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class AccountControllerItTest {
    @Autowired
    private val mockMvc: MockMvc? = null

    @Test
    fun `should return an account`() {
        val gson = Gson()
        val account = AccountDto(175, 20, 226)
        this.mockMvc?.perform(get("$ACCOUNT_SERVICE_URL/{id}", 226))
            ?.andDo(print())
            ?.andExpect(status().isOk)
            ?.andExpect(content().json(gson.toJson(account)))
    }

    @Test
    fun `should return true when check balance`() {
        this.mockMvc?.perform(get("$ACCOUNT_SERVICE_URL/balance/check")
            .queryParam("clientId", "226")
            .queryParam("cost", "10")
            .queryParam("currency", "RUR")
        )
            ?.andDo(print())
            ?.andExpect(status().isOk)
            ?.andExpect(content().string("true"))
    }

    @Test
    fun `should return false when check balance`() {
        this.mockMvc?.perform(get("$ACCOUNT_SERVICE_URL/balance/check")
            .queryParam("clientId", "226")
            .queryParam("cost", "100")
            .queryParam("currency", "RUR")
        )
            ?.andDo(print())
            ?.andExpect(status().isOk)
            ?.andExpect(content().string("false"))
    }

    @Test
    fun `should return an account with changed balance`() {
        val gson = Gson()
        val updatedAccount = AccountDto(175, 120, 226)
        this.mockMvc?.perform(put("$ACCOUNT_SERVICE_URL/balance/change/{clientId}", 226)
            .queryParam("amount", "100")
            .queryParam("currency", "RUR")
        )
            ?.andDo(print())
            ?.andExpect(status().isOk)
            ?.andExpect(content().json(gson.toJson(updatedAccount)))
    }

    companion object{
        const val ACCOUNT_SERVICE_URL = "http://localhost:8082/account"
    }

}