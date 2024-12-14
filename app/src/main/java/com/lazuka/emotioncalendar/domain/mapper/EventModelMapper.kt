package com.lazuka.emotioncalendar.domain.mapper

import com.lazuka.emotioncalendar.data.api.model.EventResponse
import com.lazuka.emotioncalendar.domain.model.EventModel
import javax.inject.Inject

class EventModelMapper @Inject constructor() {

    operator fun invoke(response: EventResponse): EventModel = with(response) {
        EventModel(
            id = id ?: 0,
            description = description.orEmpty(),
            duration = EventModel.Duration.valueOf(duration.orEmpty()),
            cost = cost ?: 0.0,
            questDay = questDay ?: false
        )
    }
}