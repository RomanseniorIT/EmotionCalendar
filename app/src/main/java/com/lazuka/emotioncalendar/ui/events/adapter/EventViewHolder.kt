package com.lazuka.emotioncalendar.ui.events.adapter

import android.graphics.PorterDuff
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.lazuka.emotioncalendar.R
import com.lazuka.emotioncalendar.databinding.ItemEventBinding
import com.lazuka.emotioncalendar.ui.events.model.EventUi

class EventViewHolder(
    private val binding: ItemEventBinding,
    private val onDoneAction: (Long) -> Unit,
    private val onNotLikeAction: (Long) -> Unit,
    private val onLaterAction: (Long) -> Unit
) : ViewHolder(binding.root) {

    fun onBind(model: EventUi) = with(binding) {
        tvEvent.text = model.description
        tvDayQuest.isVisible = model.questDay
        tvCost.text = model.getCostText(root.context)
        tvCost.isVisible = model.isCostVisible
        tvTime.text = model.getDurationText(root.context)

        if (model.completed) {
            layoutContent.setBackgroundResource(R.color.completed_event_background_color)
            ivComplete.setColorFilter(ContextCompat.getColor(root.context, R.color.completed_color))
            ivNotLike.setColorFilter(ContextCompat.getColor(root.context, R.color.completed_frame_color), PorterDuff.Mode.MULTIPLY)
            ivLater.setColorFilter(ContextCompat.getColor(root.context, R.color.completed_frame_color), PorterDuff.Mode.MULTIPLY)
            tvEvent.setTextColor(ContextCompat.getColor(root.context, R.color.purpleColor50))
            tvDayQuest.setTextColor(ContextCompat.getColor(root.context, R.color.purpleColor50))
            tvDayQuest.setBackgroundResource(R.drawable.bg_rounded_frame_complete)
            tvCost.setTextColor(ContextCompat.getColor(root.context, R.color.purpleColor50))
            tvCost.setBackgroundResource(R.drawable.bg_rounded_frame_complete)
            tvTime.setTextColor(ContextCompat.getColor(root.context, R.color.purpleColor50))
            tvTime.setBackgroundResource(R.drawable.bg_rounded_frame_complete)
        } else {
            if (model.questDay) {
                layoutContent.setBackgroundResource(R.drawable.ic_quest_of_the_day_bg)
            }

            ivComplete.setOnClickListener {
                onDoneAction(model.id)
            }

            ivNotLike.setOnClickListener {
                onNotLikeAction(model.id)
            }

            ivLater.setOnClickListener {
                onLaterAction(model.id)
            }
        }
    }
}