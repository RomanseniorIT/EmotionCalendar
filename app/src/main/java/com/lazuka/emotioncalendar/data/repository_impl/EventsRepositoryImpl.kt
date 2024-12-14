package com.lazuka.emotioncalendar.data.repository_impl

import com.lazuka.emotioncalendar.data.data_source.EventsDataSource
import com.lazuka.emotioncalendar.domain.mapper.EventModelMapper
import com.lazuka.emotioncalendar.domain.model.EventModel
import com.lazuka.emotioncalendar.domain.repository.EventsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class EventsRepositoryImpl @Inject constructor(
    private val dataSource: EventsDataSource,
    private val eventModelMapper: EventModelMapper
) : EventsRepository {

    override suspend fun getEvents(userId: Long?): List<EventModel> = withContext(Dispatchers.IO) {
        dataSource.getEvents(userId).map(eventModelMapper::invoke)
    }
}