package ru.bmstu.iu7.simplemusic.subscriptionsservice.domain

import java.io.Serializable
import javax.persistence.*

@Entity
data class Subscription(
    @EmbeddedId
    val info: SubscriptionPK? = null
)

@Embeddable
class SubscriptionPK(
        val musician: String = "",
        val subscriber: String = ""
) : Serializable
