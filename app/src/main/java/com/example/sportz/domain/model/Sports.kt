package com.example.sportz.domain.model

import com.example.sportz.data.local.entity.SportsEntity

data class Sports(
    val id: Int,
    val name: String,
    val icon: String
) {
    fun toSportsEntity(): SportsEntity {
        return SportsEntity(
            name = name,
            sportsId = id,
            icon = icon
        )
    }
}