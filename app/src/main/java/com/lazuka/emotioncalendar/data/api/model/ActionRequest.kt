package com.lazuka.emotioncalendar.data.api.model

import com.squareup.moshi.Json

data class ActionRequest(

    @Json(name = "eventId")
    val eventId: Long,

    @Json(name = "action")
    val action: String
)