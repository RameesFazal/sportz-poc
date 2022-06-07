package com.example.sportz.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.sportz.domain.model.SportsDetails

@Entity
data class SportDetailsEntity(
    @PrimaryKey val id: Int? = null,
    val name: String,
    val sportsId: Int,
    val description: String,
    val sportsImage: String
) {
    fun toSportsDetails(): SportsDetails {
        return SportsDetails(
            id = sportsId,
            name = name,
            description = description,
            image = sportsImage
        )
    }
}