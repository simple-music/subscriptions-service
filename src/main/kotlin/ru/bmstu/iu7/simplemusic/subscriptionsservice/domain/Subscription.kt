package ru.bmstu.iu7.simplemusic.subscriptionsservice.domain

import java.io.Serializable
import javax.persistence.Embeddable
import javax.persistence.EmbeddedId
import javax.persistence.Entity

@Entity
data class Subscription(
        @EmbeddedId
        val info: SubscriptionPK? = null
)

@Embeddable
class SubscriptionPK(
        @Suppress(names = ["unused"])
        val musician: String = "",

        @Suppress(names = ["unused"])
        val subscriber: String = ""
) : Serializable
