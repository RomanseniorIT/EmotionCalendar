package com.lazuka.emotioncalendar.data.repository_impl

import com.lazuka.emotioncalendar.data.api.model.UserDto
import com.lazuka.emotioncalendar.data.data_source.ProfileDataSource
import com.lazuka.emotioncalendar.data.data_source.SettingsDataSource
import com.lazuka.emotioncalendar.domain.mapper.UserModelMapper
import com.lazuka.emotioncalendar.domain.model.Personality
import com.lazuka.emotioncalendar.domain.model.Sex
import com.lazuka.emotioncalendar.domain.model.UserModel
import com.lazuka.emotioncalendar.domain.repository.ProfileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val settingsDataSource: SettingsDataSource,
    private val profileDataSource: ProfileDataSource,
    private val userModelMapper: UserModelMapper
) : ProfileRepository {

    override fun isAuthorized(): Boolean {
        return settingsDataSource.getUserId() != null
    }

    override fun getUserHasFilled(): Boolean {
        return settingsDataSource.getUserHasFilled()
    }

    override fun logout() {
        settingsDataSource.removeUserId()
        settingsDataSource.removeUserHasFilled()
    }

    override fun getUserId(): Long {
        return requireNotNull(settingsDataSource.getUserId())
    }

    override suspend fun registerUser(): UserModel = withContext(Dispatchers.IO) {
        profileDataSource.registerUser().let(userModelMapper::invoke).also(::saveUserData)
    }

    override suspend fun getUserById(userId: Long): UserModel {
        val response = profileDataSource.getUser(userId)
        return userModelMapper(response).also(::saveUserData)
    }

    override suspend fun fillUser(
        adult: Boolean,
        personality: Personality,
        sex: Sex
    ): UserModel = withContext(Dispatchers.IO) {
        val userId = requireNotNull(settingsDataSource.getUserId())
        val request = UserDto(
            userId = userId,
            adult = adult,
            personality = personality.name,
            sex = sex.name
        )
        val response = profileDataSource.fillUser(userId, request)
        userModelMapper(response).also(::saveUserData)
    }

    private fun saveUserData(user: UserModel) {
        settingsDataSource.setUserId(user.userId)
        settingsDataSource.setUserHasFilled(user.hasFilled)
    }
}