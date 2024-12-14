package com.lazuka.emotioncalendar.data.repository_impl

import com.lazuka.emotioncalendar.data.api.model.ActionRequest
import com.lazuka.emotioncalendar.data.api.model.StatusRequest
import com.lazuka.emotioncalendar.data.data_source.EventsDataSource
import com.lazuka.emotioncalendar.data.data_source.SettingsDataSource
import com.lazuka.emotioncalendar.domain.mapper.EventModelMapper
import com.lazuka.emotioncalendar.domain.model.EventModel
import com.lazuka.emotioncalendar.domain.repository.EventsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class EventsRepositoryImpl @Inject constructor(
    private val dataSource: EventsDataSource,
    private val eventModelMapper: EventModelMapper,
    private val settingsDataSource: SettingsDataSource
) : EventsRepository {

    override suspend fun getEvents(): List<EventModel> = withContext(Dispatchers.IO) {
        val userId = settingsDataSource.getUserId()
        dataSource.getEvents(userId).map(eventModelMapper::invoke)
    }

    override suspend fun setEventStatus(
        eventId: Long,
        actionKey: String
    ): List<EventModel> = withContext(Dispatchers.IO) {
        val action = ActionRequest(eventId, actionKey)
        val request = StatusRequest(listOf(action))
        val userId = requireNotNull(settingsDataSource.getUserId())
        dataSource.setEventStatus(userId, request).map(eventModelMapper::invoke)
    }
}