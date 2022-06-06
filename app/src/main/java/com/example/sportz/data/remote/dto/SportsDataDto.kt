package com.example.sportz.data.remote.dto

import com.example.sportz.domain.model.Sports

data class SportsDataDto(
    val attributes: AttributesDto,
    val id: Int,
    val type: String
) {

    fun toSports(): Sports {
        return Sports(
            id = id,
            name = attributes.name!!,
            icon = attributes.icon!!
        )
    }
}