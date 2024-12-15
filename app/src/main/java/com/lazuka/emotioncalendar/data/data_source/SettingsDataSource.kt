package com.lazuka.emotioncalendar.data.data_source

import android.content.SharedPreferences
import javax.inject.Inject

class SettingsDataSource @Inject constructor(
    private val preferences: SharedPreferences
) {

    companion object {
        private const val USER_ID_KEY = "USER_ID_KEY"
        private const val USER_HAS_FILLED_KEY = "USER_HAS_FILLED_KEY"
        private const val NO_ID = -1L
    }

    fun setUserId(userId: Long) {
        preferences.edit().putLong(USER_ID_KEY, userId).apply()
    }

    fun getUserId(): Long? {
        val userId = preferences.getLong(USER_ID_KEY, NO_ID)
        return if (userId != NO_ID) userId else null
    }

    fun removeUserId() {
        preferences.edit().remove(USER_ID_KEY).apply()
    }

    fun setUserHasFilled(hasFilled: Boolean) {
        preferences.edit().putBoolean(USER_HAS_FILLED_KEY, hasFilled).apply()
    }

    fun getUserHasFilled(): Boolean {
        return preferences.getBoolean(USER_HAS_FILLED_KEY, false)
    }

    fun removeUserHasFilled() {
        preferences.edit().remove(USER_HAS_FILLED_KEY).apply()
    }
}