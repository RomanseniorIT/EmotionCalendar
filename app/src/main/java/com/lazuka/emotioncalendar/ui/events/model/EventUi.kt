package com.lazuka.emotioncalendar.ui.events.model

import android.content.Context
import com.lazuka.emotioncalendar.R
import com.lazuka.emotioncalendar.domain.model.EventDuration

data class EventUi(
    val id: Long,
    val description: String,
    private val duration: EventDuration,
    private val cost: Double,
    val questDay: Boolean,
    val completed: Boolean
) {

    val isCostVisible = cost > 0

    fun getCostText(context: Context): String = context.getString(R.string.event_money, cost.toString())

    fun getDurationText(context: Context): String = context.getString(
        when (duration) {
            EventDuration.FEW_MINUTES -> R.string.few_minutes
            EventDuration.ONE_HOUR -> R.string.until_hour
            EventDuration.FEW_HOURS -> R.string.more_hour
            EventDuration.DAY -> R.string.one_day_long
        }
    )
}
