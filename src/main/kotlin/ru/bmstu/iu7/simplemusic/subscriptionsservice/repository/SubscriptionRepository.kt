package ru.bmstu.iu7.simplemusic.subscriptionsservice.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import ru.bmstu.iu7.simplemusic.subscriptionsservice.domain.Subscription
import ru.bmstu.iu7.simplemusic.subscriptionsservice.domain.SubscriptionPK

interface SubscriptionRepository : JpaRepository<Subscription, SubscriptionPK> {
    fun countSubscriptionsByInfoMusician(musician: String): Long
    fun countSubscriptionsByInfoSubscriber(subscriber: String): Long
    fun findSubscriptionsByInfoMusician(musician: String, page: Pageable): Page<Subscription>
    fun findSubscriptionsByInfoSubscriber(subscriber: String, page: Pageable): Page<Subscription>
}
