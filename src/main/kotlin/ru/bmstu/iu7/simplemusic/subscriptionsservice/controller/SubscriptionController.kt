package ru.bmstu.iu7.simplemusic.subscriptionsservice.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.bmstu.iu7.simplemusic.subscriptionsservice.exception.ValidationException
import ru.bmstu.iu7.simplemusic.subscriptionsservice.model.SubscriptionsStatus
import ru.bmstu.iu7.simplemusic.subscriptionsservice.service.SubscriptionService
import ru.bmstu.iu7.simplemusic.subscriptionsservice.domain.Subscription as SubscriptionDomain


@RestController
@RequestMapping("/musicians/{musician}")
class SubscriptionController(@Autowired val subscriptionService: SubscriptionService) {
    @PostMapping("/subscribers/{subscriber}")
    fun addSubscription(@PathVariable("musician") musician: String,
                        @PathVariable("subscriber") subscriber: String): ResponseEntity<SubscriptionsStatus> {
        val status = this.subscriptionService.addSubscription(musician, subscriber)
        return ResponseEntity.ok(status)
    }

    @GetMapping("/status")
    fun getSubscriptionsStatus(@PathVariable(value = "musician") musician: String): ResponseEntity<SubscriptionsStatus> {
        val status = this.subscriptionService.getSubscriptionsStatus(musician)
        return ResponseEntity.ok(status)
    }

    @GetMapping("/subscribers")
    fun getSubscribers(@PathVariable(value = "musician") musician: String,
                       @RequestParam(value = "page", required = false, defaultValue = "0") page: Int,
                       @RequestParam(value = "size", required = false, defaultValue = "10") size: Int): ResponseEntity<Iterable<String>> {
        this.validatePage(page, size)
        val subscriptions = this.subscriptionService.getSubscribers(musician, page, size)
        return ResponseEntity.ok(subscriptions)
    }

    @GetMapping("/subscriptions")
    fun getSubscriptions(@PathVariable(value = "musician") musician: String,
                         @RequestParam(value = "page", required = false, defaultValue = "0") page: Int,
                         @RequestParam(value = "size", required = false, defaultValue = "10") size: Int): ResponseEntity<Iterable<String>> {
        this.validatePage(page, size)
        val subscriptions = this.subscriptionService.getSubscriptions(musician, page, size)
        return ResponseEntity.ok(subscriptions)
    }

    @DeleteMapping("/subscriptions/{subscription}")
    fun deleteSubscription(@PathVariable("musician") musician: String,
                           @PathVariable("subscription") subscription: String): ResponseEntity<SubscriptionsStatus?> {
        val status = this.subscriptionService.deleteSubscription(subscription, musician)
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
