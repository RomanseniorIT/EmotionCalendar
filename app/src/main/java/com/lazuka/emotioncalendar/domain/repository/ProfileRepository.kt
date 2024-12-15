package com.lazuka.emotioncalendar.domain.repository

import com.lazuka.emotioncalendar.domain.model.Personality
import com.lazuka.emotioncalendar.domain.model.Sex
import com.lazuka.emotioncalendar.domain.model.UserModel

interface ProfileRepository {

    fun isAuthorized(): Boolean

    fun getUserHasFilled(): Boolean

    fun logout()

    fun getUserId(): Long

    suspend fun registerUser(): UserModel

    suspend fun getUserById(userId: Long): UserModel

    suspend fun fillUser(adult: Boolean, personality: Personality, sex: Sex): UserModel
}