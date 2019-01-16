package ru.bmstu.iu7.simplemusic.subscriptionsservice.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.bmstu.iu7.simplemusic.subscriptionsservice.exception.ValidationException
import ru.bmstu.iu7.simplemusic.subscriptionsservice.model.Subscription
import ru.bmstu.iu7.simplemusic.subscriptionsservice.model.SubscriptionsStatus
import ru.bmstu.iu7.simplemusic.subscriptionsservice.service.SubscriptionService
import ru.bmstu.iu7.simplemusic.subscriptionsservice.domain.Subscription as SubscriptionDomain


@RestController
@RequestMapping("/subscriptions")
class SubscriptionController(@Autowired val subscriptionService: SubscriptionService) {
    @PostMapping
    fun addSubscription(@RequestBody subscription: Subscription): ResponseEntity<SubscriptionsStatus> {
        val status = this.subscriptionService.addSubscription(subscription)
        return ResponseEntity.ok(status)
    }

    @GetMapping("/musicians/{musician}/status")
    fun getSubscriptionsStatus(@PathVariable(value = "musician") musician: String): ResponseEntity<SubscriptionsStatus> {
        val status = this.subscriptionService.getSubscriptionsStatus(musician)
        return ResponseEntity.ok(status)
    }

    @GetMapping("/musicians/{musician}/subscribers")
    fun getSubscribers(@PathVariable(value = "musician") musician: String,
                       @RequestParam(value = "page", required = false, defaultValue = "0") page: Int,
                       @RequestParam(value = "size", required = false, defaultValue = "10") size: Int): ResponseEntity<Iterable<String>> {
        this.validatePage(page, size)
        val subscriptions = this.subscriptionService.getSubscribers(musician, page, size)
        return ResponseEntity.ok(subscriptions)
    }

    @GetMapping("/musicians/{musician}/subscriptions")
    fun getSubscriptions(@PathVariable(value = "musician") musician: String,
                         @RequestParam(value = "page", required = false, defaultValue = "0") page: Int,
                         @RequestParam(value = "size", required = false, defaultValue = "10") size: Int): ResponseEntity<Iterable<String>> {
        this.validatePage(page, size)
        val subscriptions = this.subscriptionService.getSubscriptions(musician, page, size)
        return ResponseEntity.ok(subscriptions)
    }

    @DeleteMapping
    fun deleteSubscription(@RequestBody subscription: Subscription): ResponseEntity<SubscriptionsStatus?> {
        val status = this.subscriptionService.deleteSubscription(subscription)
        return if (status == null) {
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.ok(status)
        }
    }

    fun validatePage(page: Int, size: Int) {
        if (size > 50) {
            throw ValidationException("invalid page size")
        }
    }
}
