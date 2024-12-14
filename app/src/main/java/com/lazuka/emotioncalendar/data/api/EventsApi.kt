package com.lazuka.emotioncalendar.data.api

import com.lazuka.emotioncalendar.data.api.model.EventResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface EventsApi {

    @GET("/event")
    suspend fun getEvents(@Query("id") userId: Long?): List<EventResponse>
}