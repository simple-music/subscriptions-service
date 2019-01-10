package ru.bmstu.iu7.simplemusic.subscriptionsservice.service

import ru.bmstu.iu7.simplemusic.subscriptionsservice.model.NewSubscription
import ru.bmstu.iu7.simplemusic.subscriptionsservice.model.SubscriptionsStatus

interface SubscriptionService {
    fun addSubscription(newSubscription: NewSubscription): SubscriptionsStatus
    fun getSubscriptionsStatus(musician: String): SubscriptionsStatus
}
