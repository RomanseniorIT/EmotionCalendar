package com.lazuka.emotioncalendar.ui.events.mapper

import com.lazuka.emotioncalendar.domain.model.EventModel
import com.lazuka.emotioncalendar.ui.events.model.EventUi
import javax.inject.Inject

class EventUiMapper @Inject constructor() {

    operator fun invoke(model: EventModel): EventUi = with (model) {
        EventUi(
            id = id,
            description = description,
            duration = duration,
            cost = cost,
            questDay = questDay,
            completed = completed
        )
    }
}