package ru.bmstu.iu7.simplemusic.subscriptionsservice.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.bmstu.iu7.simplemusic.subscriptionsservice.model.NewSubscription
import ru.bmstu.iu7.simplemusic.subscriptionsservice.model.SubscriptionsStatus
import ru.bmstu.iu7.simplemusic.subscriptionsservice.service.SubscriptionService

@RestController
@RequestMapping("/subscriptions")
class SubscriptionController(@Autowired val subscriptionService: SubscriptionService) {
    @PostMapping
    fun addSubscription(@RequestBody newSubscription: NewSubscription): ResponseEntity<Long> {
        val numSubscriptions = this.subscriptionService.addSubscription(newSubscription)
        return if (numSubscriptions == null) {
            ResponseEntity.status(HttpStatus.NOT_MODIFIED.value()).build()
        } else {
            ResponseEntity.ok(numSubscriptions)
        }
    }

    @GetMapping("/musicians/{musician}/status")
    fun getSubscriptionsStatus(@PathVariable("musician") musician: String): ResponseEntity<SubscriptionsStatus> {
        val status = this.subscriptionService.getSubscriptionsStatus(musician)
        return ResponseEntity.ok(status)
    }
}
