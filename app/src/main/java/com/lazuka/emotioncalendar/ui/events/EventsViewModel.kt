package com.lazuka.emotioncalendar.ui.events

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lazuka.emotioncalendar.domain.model.EventModel
import com.lazuka.emotioncalendar.domain.repository.EventsRepository
import com.lazuka.emotioncalendar.domain.repository.ProfileRepository
import com.lazuka.emotioncalendar.ui.events.mapper.EventUiMapper
import com.lazuka.emotioncalendar.ui.events.model.ActionType
import com.lazuka.emotioncalendar.ui.events.model.EventUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventsViewModel @Inject constructor(
    private val eventsRepository: EventsRepository,
    private val profileRepository: ProfileRepository,
    private val eventUiMapper: EventUiMapper
) : ViewModel() {

    private val _eventsFlow = MutableSharedFlow<List<EventUi>>(replay = 1)
    val eventsFlow: SharedFlow<List<EventUi>> = _eventsFlow

    private val errorChannel = Channel<Unit>()
    val errorFlow: Flow<Unit> = errorChannel.receiveAsFlow()

    private val navigateToProfileChannel = Channel<Unit>()
    val navigateToProfileFlow: Flow<Unit> = navigateToProfileChannel.receiveAsFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val handler = CoroutineExceptionHandler { _, _ ->
        errorChannel.trySend(Unit)
    }

    init {
        getEvents()
    }

    private fun getEvents() {
        viewModelScope.launch(handler) {
            _isLoading.emit(true)
            val list = eventsRepository.getEvents().mapEvents()

            _eventsFlow.emit(list)
            _isLoading.emit(false)
        }
    }

    private fun setEventStatus(eventId: Long, action: ActionType) {
        viewModelScope.launch(handler) {
            _isLoading.emit(true)
            val list = eventsRepository.setEventStatus(eventId, action.name).mapEvents()

            _eventsFlow.emit(list)
            _isLoading.emit(false)
        }
    }

    private fun List<EventModel>.mapEvents(): List<EventUi> = map(eventUiMapper::invoke).sortedBy(EventUi::completed)

    fun onEventAction(eventId: Long, action: ActionType) {
        if (profileRepository.isAuthorized()) {
            setEventStatus(eventId, action)
        } else {
            navigateToProfileChannel.trySend(Unit)
        }
    }
}