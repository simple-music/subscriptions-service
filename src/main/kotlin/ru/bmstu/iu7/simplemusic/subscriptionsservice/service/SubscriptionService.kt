package ru.bmstu.iu7.simplemusic.subscriptionsservice.service

import ru.bmstu.iu7.simplemusic.subscriptionsservice.model.Subscription
import ru.bmstu.iu7.simplemusic.subscriptionsservice.model.SubscriptionsStatus
import ru.bmstu.iu7.simplemusic.subscriptionsservice.domain.Subscription as SubscriptionDomain

interface SubscriptionService {
    fun addSubscription(subscription: Subscription): SubscriptionsStatus
    fun getSubscriptionsStatus(musician: String): SubscriptionsStatus

    fun getSubscribers(musician: String, page: Int, size: Int): Iterable<SubscriptionDomain>
    fun getSubscriptions(musician: String, page: Int, size: Int): Iterable<SubscriptionDomain>

    fun deleteSubscription(subscription: Subscription): SubscriptionsStatus?
}
