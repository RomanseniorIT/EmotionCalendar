package com.lazuka.emotioncalendar.data.api.model

import com.squareup.moshi.Json

data class UserResponse(
    @Json(name = "id")
    val userId: Long? = null
)
