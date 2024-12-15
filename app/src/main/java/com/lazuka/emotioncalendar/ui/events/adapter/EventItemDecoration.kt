package com.lazuka.emotioncalendar.ui.events.adapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.lazuka.emotioncalendar.R

class EventItemDecoration : ItemDecoration() {

    companion object {
        private const val NO_POSITION = -1
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val adapterPosition = parent.getChildAdapterPosition(view)
        val position = if (adapterPosition != NO_POSITION) adapterPosition else parent.getChildLayoutPosition(view)
        if (position == 0) return

        val list = (parent.adapter as EventsAdapter).currentList
        val item = list.getOrNull(position) ?: return
        val firstCompletedPos = list.indexOfFirst { it.completed }
        val bigSpace = parent.context.resources.getDimensionPixelSize(R.dimen.dp_32)
        val space = parent.context.resources.getDimensionPixelSize(R.dimen.dp_12)

        when {
            item.completed && firstCompletedPos == position -> outRect.top = bigSpace
            else -> outRect.top = space
        }
    }
}