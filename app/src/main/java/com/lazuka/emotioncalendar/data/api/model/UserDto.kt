package com.lazuka.emotioncalendar.data.api.model

import com.squareup.moshi.Json

data class UserDto(
    @Json(name = "id")
    val userId: Long? = null,

    @Json(name = "adult")
    val adult: Boolean? = null,

    @Json(name = "personality")
    val personality: String? = null,

    @Json(name = "sex")
    val sex: String? = null
)
