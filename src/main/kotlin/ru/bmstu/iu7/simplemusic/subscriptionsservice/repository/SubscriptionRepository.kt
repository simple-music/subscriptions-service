package ru.bmstu.iu7.simplemusic.subscriptionsservice.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import ru.bmstu.iu7.simplemusic.subscriptionsservice.domain.Subscription
import ru.bmstu.iu7.simplemusic.subscriptionsservice.domain.SubscriptionPK
import java.util.*

interface SubscriptionRepository : JpaRepository<Subscription, SubscriptionPK> {
    @Query(value = """SELECT * FROM "subscribers_summary" WHERE "user" = #user;""", nativeQuery = true)
    fun countSubscribersByUser(user: String): Optional<Long>

    @Query(value = """SELECT * FROM "subscriptions_summary" WHERE "user" = #user;""", nativeQuery = true)
    fun countSubscriptionsByUser(user: String): Optional<Long>
}
