package com.lazuka.emotioncalendar.data.api.model

import com.squareup.moshi.Json

data class EventResponse(
    @Json(name = "id")
    val id: Long? = null,

    @Json(name = "description")
    val description: String? = null,

    @Json(name = "duration")
    val duration: String? = null,

    @Json(name = "cost")
    val cost: Double? = null,

    @Json(name = "questDay")
    val questDay: Boolean? = null
)
