package ru.bmstu.iu7.simplemusic.subscriptionsservice.model

import com.fasterxml.jackson.annotation.JsonProperty
import lombok.Data

@Data
data class SubscriptionsStatus (
        @JsonProperty(value = "user")
        val user: String,

        @JsonProperty(value = "numSubscribers")
        val numSubscribers: Long,

        @JsonProperty(value = "numSubscriptions")
        val numSubscriptions: Long
)
