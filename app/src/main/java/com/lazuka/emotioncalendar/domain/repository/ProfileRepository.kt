package com.lazuka.emotioncalendar.domain.repository

interface ProfileRepository {

    fun isAuthorized(): Boolean
}