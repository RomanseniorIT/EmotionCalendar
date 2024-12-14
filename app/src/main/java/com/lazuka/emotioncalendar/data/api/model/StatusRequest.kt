package com.lazuka.emotioncalendar.data.api.model

import com.squareup.moshi.Json

data class StatusRequest(

    @Json(name = "items")
    val actions: List<ActionRequest>
)
