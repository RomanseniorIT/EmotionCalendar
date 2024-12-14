package com.lazuka.emotioncalendar.data.api

import com.lazuka.emotioncalendar.data.api.model.EventResponse
import com.lazuka.emotioncalendar.data.api.model.StatusRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface EventsApi {

    @GET("/event")
    suspend fun getEvents(@Query("id") userId: Long?): List<EventResponse>

    @POST("/event/set-status/{id}")
    suspend fun setEventStatus(@Path("id") userId: Long, @Body request: StatusRequest): List<EventResponse>
}