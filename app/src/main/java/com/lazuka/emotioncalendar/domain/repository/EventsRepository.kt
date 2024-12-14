package com.lazuka.emotioncalendar.domain.repository

import com.lazuka.emotioncalendar.domain.model.EventModel

interface EventsRepository {

    suspend fun getEvents(): List<EventModel>

    suspend fun setEventStatus(eventId: Long, actionKey: String): List<EventModel>
}