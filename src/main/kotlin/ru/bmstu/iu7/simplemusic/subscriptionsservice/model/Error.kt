package ru.bmstu.iu7.simplemusic.subscriptionsservice.model

import com.fasterxml.jackson.annotation.JsonProperty
import lombok.Data

@Data
data class Error (
        @JsonProperty(value = "message")
        val message: String
)
