package com.lazuka.emotioncalendar.ui.events.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.lazuka.emotioncalendar.databinding.ItemEventBinding
import com.lazuka.emotioncalendar.ui.events.model.ActionType
import com.lazuka.emotioncalendar.ui.events.model.EventUi

class EventsAdapter(
    private val onAction: (Long, ActionType) -> Unit
) : ListAdapter<EventUi, EventViewHolder>(DIFF_UTIL_CALLBACK) {

    companion object {
        private val DIFF_UTIL_CALLBACK = object : DiffUtil.ItemCallback<EventUi>() {
            override fun areItemsTheSame(oldItem: EventUi, newItem: EventUi): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: EventUi, newItem: EventUi): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding = ItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventViewHolder(binding, onAction)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.onBind(currentList[position])
    }
}