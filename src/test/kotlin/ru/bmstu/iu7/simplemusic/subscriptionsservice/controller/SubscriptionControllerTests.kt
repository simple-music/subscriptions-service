package ru.bmstu.iu7.simplemusic.subscriptionsservice.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import ru.bmstu.iu7.simplemusic.subscriptionsservice.constant.Constant
import ru.bmstu.iu7.simplemusic.subscriptionsservice.exception.NotFoundException
import ru.bmstu.iu7.simplemusic.subscriptionsservice.model.Error
import ru.bmstu.iu7.simplemusic.subscriptionsservice.model.SubscriptionsStatus
import ru.bmstu.iu7.simplemusic.subscriptionsservice.service.SubscriptionService
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.abs
import kotlin.random.Random

@RunWith(SpringRunner::class)
@WebMvcTest(controllers = [SubscriptionController::class], secure = false)
class MusicianControllerTests {
    @Autowired
    private val mockMvc: MockMvc? = null

    @MockBean
    private val mockService: SubscriptionService? = null

    private val notFoundException = NotFoundException("error message")
    private val notFoundErrorStr = this.mapObject(Error(this.notFoundException.message!!))

    @Test
    fun addSubscription() {
        val user = this.generateUser()
        val subscription = this.generateUser()

        val status = this.generateStatus(subscription)
        val statusStr = this.mapObject(status)

        Mockito
                .`when`(this.mockService!!.addSubscription(subscription, user))
                .thenReturn(status)

        this.mockMvc!!
                .perform(MockMvcRequestBuilders
                        .post("/users/$user/subscriptions/$subscription"))
                .andExpect(MockMvcResultMatchers
                        .status().isOk)
                .andExpect(MockMvcResultMatchers
                        .content().string(statusStr))
    }

    @Test
    fun deleteSubscription() {
        val user = this.generateUser()
        val subscription = this.generateUser()

        val status = this.generateStatus(subscription)
        val statusStr = this.mapObject(status)

        Mockito
                .`when`(this.mockService!!.deleteSubscription(subscription, user))
                .thenReturn(status)

        this.mockMvc!!
                .perform(MockMvcRequestBuilders
                        .delete("/users/$user/subscriptions/$subscription"))
                .andExpect(MockMvcResultMatchers
                        .status().isOk)
                .andExpect(MockMvcResultMatchers
                        .content().string(statusStr))

        Mockito
                .`when`(this.mockService.deleteSubscription(subscription, user))
                .thenReturn(null)

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/users/$user/subscriptions/$subscription"))
                .andExpect(MockMvcResultMatchers
                        .status().isNoContent)

        Mockito
                .`when`(this.mockService.deleteSubscription(subscription, user))
                .thenThrow(this.notFoundException)

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/users/$user/subscriptions/$subscription"))
                .andExpect(MockMvcResultMatchers
                        .status().isNotFound)
                .andExpect(MockMvcResultMatchers
                        .content().string(this.notFoundErrorStr))
    }

    @Test
    fun getStatus() {
        val user = this.generateUser()

        val status = this.generateStatus(user)
        val statusStr = this.mapObject(status)

        Mockito
                .`when`(this.mockService!!.getSubscriptionsStatus(user))
                .thenReturn(status)

        this.mockMvc!!
                .perform(MockMvcRequestBuilders
                        .get("/users/$user/status"))
                .andExpect(MockMvcResultMatchers
                        .status().isOk)
                .andExpect(MockMvcResultMatchers
                        .content().string(statusStr))

        Mockito
                .`when`(this.mockService.getSubscriptionsStatus(user))
                .thenThrow(this.notFoundException)

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/users/$user/status"))
                .andExpect(MockMvcResultMatchers
                        .status().isNotFound)
                .andExpect(MockMvcResultMatchers
                        .content().string(this.notFoundErrorStr))
    }

    @Test
    fun getSubscribers() {
        val user = this.generateUser()

        val subscribers = this.generateUsers()
        val subscribersStr = this.mapObject(subscribers)

        Mockito
                .`when`(this.mockService!!.getSubscribers(
                        Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt())
                ).thenReturn(subscribers)

        this.mockMvc!!
                .perform(MockMvcRequestBuilders
                        .get("/users/$user/subscribers"))
                .andExpect(MockMvcResultMatchers
                        .status().isOk)
                .andExpect(MockMvcResultMatchers
                        .content().string(subscribersStr))

        Mockito
                .`when`(this.mockService.getSubscribers(
                        Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt())
                ).thenThrow(this.notFoundException)

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/users/$user/subscribers"))
                .andExpect(MockMvcResultMatchers
                        .status().isNotFound)
                .andExpect(MockMvcResultMatchers
                        .content().string(this.notFoundErrorStr))

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/users/$user/subscribers?page=-1"))
                .andExpect(MockMvcResultMatchers
                        .status().isForbidden)

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/users/$user/subscribers?size=0"))
                .andExpect(MockMvcResultMatchers
                        .status().isForbidden)
        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/users/$user/subscribers?size=${Constant.PAGE_SIZE_LIMIT + 1}"))
                .andExpect(MockMvcResultMatchers
                        .status().isForbidden)
    }

    @Test
    fun getSubscriptions() {
        val user = this.generateUser()

        val subscriptions = this.generateUsers()
        val subscriptionsStr = this.mapObject(subscriptions)

        Mockito
                .`when`(this.mockService!!.getSubscriptions(
                        Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt())
                ).thenReturn(subscriptions)

        this.mockMvc!!
                .perform(MockMvcRequestBuilders
                        .get("/users/$user/subscriptions"))
                .andExpect(MockMvcResultMatchers
                        .status().isOk)
                .andExpect(MockMvcResultMatchers
                        .content().string(subscriptionsStr))

        Mockito
                .`when`(this.mockService.getSubscriptions(
                        Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt())
                ).thenThrow(this.notFoundException)

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/users/$user/subscriptions"))
                .andExpect(MockMvcResultMatchers
                        .status().isNotFound)
                .andExpect(MockMvcResultMatchers
                        .content().string(this.notFoundErrorStr))

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/users/$user/subscriptions?page=-1"))
                .andExpect(MockMvcResultMatchers
                        .status().isForbidden)

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/users/$user/subscribers?size=0"))
                .andExpect(MockMvcResultMatchers
                        .status().isForbidden)
        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/users/$user/subscriptions?size=${Constant.PAGE_SIZE_LIMIT + 1}"))
                .andExpect(MockMvcResultMatchers
                        .status().isForbidden)
    }

    @Test
    fun deleteSubscribersAndSubscriptions() {
        val user = this.generateUser()

        Mockito
                .doNothing().`when`(this.mockService)
                ?.deleteSubscribersAndSubscriptions(user)

        this.mockMvc!!
                .perform(MockMvcRequestBuilders
                        .delete("/users/$user"))
                .andExpect(MockMvcResultMatchers
                        .status().isNoContent)

        Mockito
                .doThrow(this.notFoundException).`when`(this.mockService)
                ?.deleteSubscribersAndSubscriptions(user)

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/users/$user"))
                .andExpect(MockMvcResultMatchers
                        .status().isNotFound)
                .andExpect(MockMvcResultMatchers
                        .content().string(this.notFoundErrorStr))
    }

    private fun mapObject(obj: Any): String {
        return ObjectMapper().writeValueAsString(obj)
    }

    private fun generateUser(): String {
        return UUID.randomUUID().toString()
    }

    private fun generateUsers(numUsers: Int = 10): Iterable<String> {
        val list = ArrayList<String>()
        for (i in 1..numUsers) {
            list.add(this.generateUser())
        }
        return list
    }

    private fun generateStatus(musician: String = this.generateUser()): SubscriptionsStatus {
        return SubscriptionsStatus(
                user = musician,
                numSubscribers = this.generateLong(),
                numSubscriptions = this.generateLong()
        )
    }

    private fun generateLong(): Long {
        return abs(Random(seed = 0).nextLong())
    }
}
