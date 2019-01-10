package ru.bmstu.iu7.simplemusic.subscriptionsservice.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.bmstu.iu7.simplemusic.subscriptionsservice.model.NewSubscription

@RestController
@RequestMapping("/subscriptions")
class SubscriptionController {
    @PostMapping
    fun addSubscription(@RequestBody newSubscription: NewSubscription): ResponseEntity<Long> {
        TODO("not implemented")
    }
}
