package ru.bmstu.iu7.simplemusic.subscriptionsservice.service

import ru.bmstu.iu7.simplemusic.subscriptionsservice.model.SubscriptionsStatus

interface SubscriptionService {
    fun addSubscription(user: String, subscriber: String): Long
    fun getSubscriptionsStatus(user: String): SubscriptionsStatus
}
