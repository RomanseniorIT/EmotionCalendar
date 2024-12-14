package com.lazuka.emotioncalendar.domain.model

data class EventModel(
    val id: Long,
    val description: String,
    val duration: EventDuration,
    val cost: Double,
    val questDay: Boolean,
    val completed: Boolean
)