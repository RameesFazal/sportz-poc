package com.example.sportz.domain.model

import com.example.sportz.data.local.entity.SportDetailsEntity

data class SportsDetails(
    val id: Int,
    val name: String,
    val description: String,
    val image: String
) {
    fun toSportsDetailsEntity(): SportDetailsEntity {
        return SportDetailsEntity(
            name = name,
            sportsId = id,
            description = description,
            sportsImage = image
        )
    }
}