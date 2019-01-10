package ru.bmstu.iu7.simplemusic.subscriptionsservice.service

import ru.bmstu.iu7.simplemusic.subscriptionsservice.model.SubscriptionsStatus

interface SubscriptionService {
    fun getSubscriptionsStatus(user: String): SubscriptionsStatus
}
