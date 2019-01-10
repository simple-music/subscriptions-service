package ru.bmstu.iu7.simplemusic.subscriptionsservice.domain

import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "subscription")
data class Subscription(
    @EmbeddedId
    val info: SubscriptionPK
)

@Embeddable
class SubscriptionPK(
        @Column(name = "musician")
        val musician: String,
        @Column(name = "subscriber")
        val subscriber: String
) : Serializable
