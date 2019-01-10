package ru.bmstu.iu7.simplemusic.subscriptionsservice.model

import com.fasterxml.jackson.annotation.JsonProperty
import lombok.Data

@Data
data class Subscription(
        @JsonProperty(value = "musician", required = true)
        val musician: String,

        @JsonProperty(value = "subscriber", required = true)
        val subscriber: String
)

@Data
data class SubscriptionsStatus (
        @JsonProperty(value = "musician")
        val musician: String,

        @JsonProperty(value = "numSubscribers")
        val numSubscribers: Long,

        @JsonProperty(value = "numSubscriptions")
        val numSubscriptions: Long
)
