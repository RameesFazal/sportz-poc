package com.example.sportz.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.sportz.domain.model.Sports

@Entity
data class SportsEntity(
    @PrimaryKey val id: Int? = null,
    val name: String,
    val sportsId: Int,
    val icon: String
) {
    fun toSports(): Sports {
        return Sports(
            id = sportsId,
            name = name,
            icon = icon
        )
    }
}