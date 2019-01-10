package ru.bmstu.iu7.simplemusic.subscriptionsservice.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.bmstu.iu7.simplemusic.subscriptionsservice.domain.Subscription
import ru.bmstu.iu7.simplemusic.subscriptionsservice.domain.SubscriptionPK

interface SubscriptionRepository : JpaRepository<Subscription, SubscriptionPK> {
   fun countSubscriptionsByInfo_Musician(user: String): Long
   fun countSubscriptionsByInfo_Subscriber(subscriber: String): Long
}
