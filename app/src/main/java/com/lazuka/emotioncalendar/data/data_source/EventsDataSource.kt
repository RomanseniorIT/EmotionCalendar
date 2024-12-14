package com.lazuka.emotioncalendar.data.data_source

import com.lazuka.emotioncalendar.data.api.EventsApi
import com.lazuka.emotioncalendar.data.api.model.EventResponse
import javax.inject.Inject

class EventsDataSource @Inject constructor(
    private val api: EventsApi
) {

    suspend fun getEvents(userId: Long?): List<EventResponse> {
        return api.getEvents(userId)
    }
}