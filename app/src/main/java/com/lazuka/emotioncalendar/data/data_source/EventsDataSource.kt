package com.lazuka.emotioncalendar.data.data_source

import com.lazuka.emotioncalendar.data.api.EventsApi
import com.lazuka.emotioncalendar.data.api.model.EventResponse
import com.lazuka.emotioncalendar.data.api.model.StatusRequest
import javax.inject.Inject

class EventsDataSource @Inject constructor(
    private val api: EventsApi
) {

    suspend fun getEvents(userId: Long?): List<EventResponse> {
        return api.getEvents(userId)
    }

    suspend fun setEventStatus(userId: Long, request: StatusRequest): List<EventResponse> {
        return api.setEventStatus(userId, request)
    }
}