package com.lazuka.emotioncalendar.data.api

import com.lazuka.emotioncalendar.data.api.model.UserResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ProfileApi {

    @POST("/user")
    suspend fun registerUser(@Body request: Any = Any()): UserResponse
}