package ru.bmstu.iu7.simplemusic.subscriptionsservice.service

import org.springframework.data.domain.Page
import ru.bmstu.iu7.simplemusic.subscriptionsservice.model.SubscriptionsStatus

interface SubscriptionService {
    fun addSubscription(musician: String, subscriber: String): SubscriptionsStatus
    fun checkSubscription(musician: String, subscriber: String)
    fun deleteSubscription(musician: String, subscriber: String): SubscriptionsStatus?

    fun getSubscriptionsStatus(musician: String): SubscriptionsStatus

    fun getSubscribers(musician: String, page: Int, size: Int): Page<String>
    fun getSubscriptions(musician: String, page: Int, size: Int): Page<String>

    fun deleteSubscribersAndSubscriptions(musician: String)
}
