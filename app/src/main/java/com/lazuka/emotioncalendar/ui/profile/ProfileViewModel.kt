package com.lazuka.emotioncalendar.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lazuka.emotioncalendar.R
import com.lazuka.emotioncalendar.domain.model.Personality
import com.lazuka.emotioncalendar.domain.model.Sex
import com.lazuka.emotioncalendar.domain.model.UserModel
import com.lazuka.emotioncalendar.domain.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository
) : ViewModel() {

    // region auth
    private val _isAuthorizedFlow = MutableStateFlow(profileRepository.isAuthorized())
    val isAuthorizedFlow: StateFlow<Boolean> = _isAuthorizedFlow

    private val errorChannel = Channel<Int>()
    val errorFlow: Flow<Int> = errorChannel.receiveAsFlow()

    private val _regSuccessFlow = MutableSharedFlow<Long>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    @OptIn(ExperimentalCoroutinesApi::class)
    val regSuccessFlow: Flow<Long> = _regSuccessFlow.onEach { _regSuccessFlow.resetReplayCache() }

    private val fillSuccessChannel = Channel<Unit>()
    val fillSuccessFlow: Flow<Unit> = fillSuccessChannel.receiveAsFlow()

    private val navigateToMainChannel = Channel<Unit>()
    val navigateToMainFlow: Flow<Unit> = navigateToMainChannel.receiveAsFlow()

    private var userIdForAuth: Long = 0
    // end region

    // region profile
    private val _userFlow = MutableSharedFlow<UserModel>(replay = 1)
    val userFlow: SharedFlow<UserModel> = _userFlow

    private val authorizedUserIdChannel = Channel<Long>()
    val authorizedUserIdFlow: Flow<Long> = authorizedUserIdChannel.receiveAsFlow()
    // end region

    // region fill profile
    private var isAdult = false
    private var personality = Personality.NOT_FILLED
    private var sex = Sex.NOT_FILLED
    // end region

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val handler = CoroutineExceptionHandler { _, _ ->
        errorChannel.trySend(R.string.error)
        _isLoading.tryEmit(false)
    }

    init {
        if (profileRepository.isAuthorized()) getUser()
    }

    private fun getUser() {
        viewModelScope.launch(handler) {
            _isLoading.emit(true)

            val userId = profileRepository.getUserId()
            val user = profileRepository.getUserById(userId)
            _userFlow.emit(user)

            _isLoading.emit(false)
        }
    }

    private fun fillUserData() {
        viewModelScope.launch(handler) {
            _isLoading.emit(true)

            profileRepository.fillUser(isAdult, personality, sex)

            fillSuccessChannel.send(Unit)
            navigateToMainChannel.send(Unit)
            _isLoading.emit(false)
        }
    }

    fun onIdChanged(id: String) {
        if (id.isBlank()) return

        try {
            this.userIdForAuth = id.toLong()
        } catch (e: Exception) {
            errorChannel.trySend(R.string.input_error)
        }
    }

    fun register() {
        viewModelScope.launch(handler) {
            _isLoading.emit(true)

            val userId = profileRepository.registerUser()

            _regSuccessFlow.emit(userId)
            _isAuthorizedFlow.emit(profileRepository.isAuthorized())
            _isLoading.emit(false)
        }
    }

    fun login() {
        viewModelScope.launch(handler) {
            _isLoading.emit(true)

            profileRepository.getUserById(userIdForAuth)

            navigateToMainChannel.send(Unit)
            _isLoading.emit(false)
        }
    }

    fun logout() {
        profileRepository.logout()
        navigateToMainChannel.trySend(Unit)
    }

    fun onSaveClicked() {
        when {
            personality == Personality.NOT_FILLED -> errorChannel.trySend(R.string.personality_input_error)
            sex == Sex.NOT_FILLED -> errorChannel.trySend(R.string.sex_input_error)
            else -> fillUserData()
        }
    }

    fun onAdultChecked(checked: Boolean) {
        isAdult = checked
    }

    fun onPersonalityChanged(personality: Personality) {
        this.personality = personality
    }

    fun onSexChanged(sex: Sex) {
        this.sex = sex
    }
}