package com.lazuka.emotioncalendar.data.api

import com.lazuka.emotioncalendar.data.api.model.UserDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface ProfileApi {

    @POST("/user")
    suspend fun registerUser(@Body request: Any = Any()): UserDto

    @GET("/user/{userId}")
    suspend fun getUser(@Path("userId") userId: Long): UserDto

    @PATCH("/user/{userId}")
    suspend fun fillUser(@Path("userId") userId: Long, @Body request: UserDto): UserDto
}