package com.lazuka.emotioncalendar.data.data_source

import com.lazuka.emotioncalendar.data.api.ProfileApi
import com.lazuka.emotioncalendar.data.api.model.UserDto
import javax.inject.Inject

class ProfileDataSource @Inject constructor(
    private val api: ProfileApi
) {

    suspend fun registerUser(): UserDto {
        return api.registerUser()
    }

    suspend fun getUser(userId: Long): UserDto {
        return api.getUser(userId)
    }

    suspend fun fillUser(userId: Long, request: UserDto): UserDto {
        return api.fillUser(userId, request)
    }
}