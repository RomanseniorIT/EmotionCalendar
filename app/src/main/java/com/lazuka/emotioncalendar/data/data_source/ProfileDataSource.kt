package com.lazuka.emotioncalendar.data.data_source

import com.lazuka.emotioncalendar.data.api.ProfileApi
import javax.inject.Inject

class ProfileDataSource @Inject constructor(
    private val api: ProfileApi
) {

    suspend fun registerUser() {
        api.registerUser()
    }
}