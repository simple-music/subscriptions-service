package ru.bmstu.iu7.simplemusic.subscriptionsservice.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.bmstu.iu7.simplemusic.subscriptionsservice.domain.Subscription
import ru.bmstu.iu7.simplemusic.subscriptionsservice.domain.SubscriptionPK

interface SubscriptionRepository : JpaRepository<Subscription, SubscriptionPK> {
   fun countSubscriptionsByInfoUser(user: String): Long
   fun countSubscriptionsByInfoSubscriber(subscriber: String): Long
}
