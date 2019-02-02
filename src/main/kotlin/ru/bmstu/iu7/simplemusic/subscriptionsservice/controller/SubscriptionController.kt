package ru.bmstu.iu7.simplemusic.subscriptionsservice.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.bmstu.iu7.simplemusic.subscriptionsservice.constant.Constant
import ru.bmstu.iu7.simplemusic.subscriptionsservice.exception.ValidationException
import ru.bmstu.iu7.simplemusic.subscriptionsservice.model.SubscriptionsStatus
import ru.bmstu.iu7.simplemusic.subscriptionsservice.service.SubscriptionService
import ru.bmstu.iu7.simplemusic.subscriptionsservice.domain.Subscription as SubscriptionDomain

@RestController
@RequestMapping(value = ["/users/{user}"])
class SubscriptionController(@Autowired val subscriptionService: SubscriptionService) {
    @PostMapping(value = ["/subscriptions/{subscription}"])
    fun addSubscription(@PathVariable(value = "user") user: String,
                        @PathVariable(value = "subscription") subscription: String): ResponseEntity<SubscriptionsStatus> {
        val status = this.subscriptionService.addSubscription(subscription, user)
        return ResponseEntity.ok(status)
    }

    @GetMapping(value = ["/subscriptions/{subscription}"])
    fun checkSubscription(@PathVariable(value = "user") user: String,
                          @PathVariable(value = "subscription") subscription: String): ResponseEntity<Any> {
        this.subscriptionService.checkSubscription(subscription, user)
        return ResponseEntity.ok().build()
    }

    @DeleteMapping(value = ["/subscriptions/{subscription}"])
    fun deleteSubscription(@PathVariable(value = "user") user: String,
                           @PathVariable(value = "subscription") subscription: String): ResponseEntity<SubscriptionsStatus?> {
        val status = this.subscriptionService.deleteSubscription(subscription, user)
        return if (status == null) {
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.ok(status)
        }
    }

    @GetMapping(value = ["/status"])
    fun getSubscriptionsStatus(@PathVariable(value = "user") user: String): ResponseEntity<SubscriptionsStatus> {
        val status = this.subscriptionService.getSubscriptionsStatus(user)
        return ResponseEntity.ok(status)
    }

    @GetMapping(value = ["/subscribers"])
    fun getSubscribers(@PathVariable(value = "user") user: String,
                       @RequestParam(value = "page", required = false,
                               defaultValue = Constant.DEFAULT_PAGE_INDEX.toString()) page: Int,
                       @RequestParam(value = "size", required = false,
                               defaultValue = Constant.DEFAULT_PAGE_SIZE.toString()) size: Int): ResponseEntity<Iterable<String>> {
        this.validatePage(page, size)
        val subscriptions = this.subscriptionService.getSubscribers(user, page, size)
        return ResponseEntity.ok(subscriptions)
    }

    @GetMapping(value = ["/subscriptions"])
    fun getSubscriptions(@PathVariable(value = "user") user: String,
                         @RequestParam(value = "page", required = false,
                                 defaultValue = Constant.DEFAULT_PAGE_INDEX.toString()) page: Int,
                         @RequestParam(value = "size", required = false,
                                 defaultValue = Constant.DEFAULT_PAGE_SIZE.toString()) size: Int): ResponseEntity<Iterable<String>> {
        this.validatePage(page, size)
        val subscriptions = this.subscriptionService.getSubscriptions(user, page, size)
        return ResponseEntity.ok(subscriptions)
    }

    @DeleteMapping
    fun deleteSubscribersAndSubscriptions(@PathVariable(value = "user") user: String): ResponseEntity<Any> {
        this.subscriptionService.deleteSubscribersAndSubscriptions(user)
        return ResponseEntity.noContent().build()
    }

    fun validatePage(page: Int, size: Int) {
        if (page < 0) {
            throw ValidationException("invalid page")
        }
        if (size <= 0 || size > Constant.PAGE_SIZE_LIMIT) {
            throw ValidationException("invalid page size")
        }
    }
}
