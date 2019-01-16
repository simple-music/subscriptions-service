package ru.bmstu.iu7.simplemusic.subscriptionsservice.model

import com.fasterxml.jackson.annotation.JsonProperty

data class SubscriptionsStatus(
        @JsonProperty(value = "musician")
        val musician: String,

        @JsonProperty(value = "numSubscribers")
        val numSubscribers: Long,

        @JsonProperty(value = "numSubscriptions")
        val numSubscriptions: Long
)
