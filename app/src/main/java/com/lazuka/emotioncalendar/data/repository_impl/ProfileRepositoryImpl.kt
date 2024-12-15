package com.lazuka.emotioncalendar.data.repository_impl

import com.lazuka.emotioncalendar.data.data_source.ProfileDataSource
import com.lazuka.emotioncalendar.data.data_source.SettingsDataSource
import com.lazuka.emotioncalendar.domain.repository.ProfileRepository
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val settingsDataSource: SettingsDataSource,
    private val profileDataSource: ProfileDataSource
) : ProfileRepository {

    override fun isAuthorized(): Boolean {
        return settingsDataSource.getUserId() != null
    }
}