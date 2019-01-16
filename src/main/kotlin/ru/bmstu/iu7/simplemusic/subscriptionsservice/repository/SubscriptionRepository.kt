package ru.bmstu.iu7.simplemusic.subscriptionsservice.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import ru.bmstu.iu7.simplemusic.subscriptionsservice.domain.Subscription
import ru.bmstu.iu7.simplemusic.subscriptionsservice.domain.SubscriptionPK

interface SubscriptionRepository : JpaRepository<Subscription, SubscriptionPK> {
    fun countSubscriptionsByInfoMusician(musician: String): Long
    fun countSubscriptionsByInfoSubscriber(subscriber: String): Long

    @Query(value = "SELECT s.subscriber FROM subscription s WHERE s.musician = :musician ORDER BY s.subscriber",
            countQuery = "SELECT count(*) FROM subscription s WHERE s.musician = :musician", nativeQuery = true)
    fun findMusicianSubscribers(musician: String, page: Pageable): Page<String>

    @Query(value = "SELECT s.musician FROM subscription s WHERE s.subscriber = :musician ORDER BY s.musician",
            countQuery = "SELECT count(*) FROM subscription s WHERE s.subscriber = :musician", nativeQuery = true)
    fun findMusicianSubscriptions(musician: String, page: Pageable): Page<String>

    fun deleteSubscriptionsByInfoMusicianOrInfoSubscriber(musician: String, subscriber: String)
}
