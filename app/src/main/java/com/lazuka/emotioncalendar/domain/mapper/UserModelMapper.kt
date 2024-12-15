package com.lazuka.emotioncalendar.domain.mapper

import com.lazuka.emotioncalendar.data.api.model.UserDto
import com.lazuka.emotioncalendar.domain.model.Personality
import com.lazuka.emotioncalendar.domain.model.Sex
import com.lazuka.emotioncalendar.domain.model.UserModel
import javax.inject.Inject

class UserModelMapper @Inject constructor() {

    operator fun invoke(response: UserDto): UserModel = with(response) {
        val pers = try {
            Personality.valueOf(personality.orEmpty())
        } catch (e: Exception) {
            Personality.NOT_FILLED
        }
        val sexMapped = try {
            Sex.valueOf(sex.orEmpty())
        } catch (e: Exception) {
            Sex.NOT_FILLED
        }

        UserModel(
            userId = userId ?: throw IllegalArgumentException(),
            adult = adult ?: false,
            personality = pers,
            sex = sexMapped
        )
    }
}
