package com.lazuka.emotioncalendar.domain.model

data class EventModel(
    val id: Long,
    val description: String,
    val duration: Duration,
    val cost: Double,
    val questDay: Boolean
) {

    enum class Duration {
        FEW_MINUTES, ONE_HOUR, FEW_HOURS, DAY
    }
}