package com.lazuka.emotioncalendar.domain.model

data class UserModel(
    val userId: Long,
    val adult: Boolean,
    val personality: Personality,
    val sex: Sex
) {

    val hasFilled = personality != Personality.NOT_FILLED && sex != Sex.NOT_FILLED
}

enum class Personality {
    INTROVERT,
    EXTROVERT,
    NOT_FILLED
}

enum class Sex {
    MAN,
    WOMAN,
    NOT_FILLED
}