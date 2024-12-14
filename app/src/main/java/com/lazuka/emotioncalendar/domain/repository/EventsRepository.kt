package com.lazuka.emotioncalendar.domain.repository

import com.lazuka.emotioncalendar.domain.model.EventModel

interface EventsRepository {

    suspend fun getEvents(userId: Long? = null): List<EventModel>
}