package com.lazuka.emotioncalendar.ui.events

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lazuka.emotioncalendar.domain.repository.EventsRepository
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
    private val eventsRepository: EventsRepository
) : ViewModel() {

    private val _eventsFlow = MutableSharedFlow<String>()
    val eventsFlow: SharedFlow<String> = _eventsFlow

    private val errorChannel = Channel<Unit>()
    val errorFlow: Flow<Unit> = errorChannel.receiveAsFlow()

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
            val list = eventsRepository.getEvents(1)
            _eventsFlow.emit(list.joinToString())
            _isLoading.emit(false)
        }
    }
}