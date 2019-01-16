package ru.bmstu.iu7.simplemusic.subscriptionsservice.service

import ru.bmstu.iu7.simplemusic.subscriptionsservice.model.SubscriptionsStatus

interface SubscriptionService {
    fun addSubscription(musician: String, subscriber: String): SubscriptionsStatus
    fun deleteSubscription(musician: String, subscriber: String): SubscriptionsStatus?

    fun getSubscriptionsStatus(musician: String): SubscriptionsStatus

    fun getSubscribers(musician: String, page: Int, size: Int): Iterable<String>
    fun getSubscriptions(musician: String, page: Int, size: Int): Iterable<String>
}
