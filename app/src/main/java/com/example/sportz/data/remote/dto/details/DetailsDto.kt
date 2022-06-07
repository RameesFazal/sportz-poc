package com.example.sportz.data.remote.dto.details

import com.example.sportz.data.remote.dto.listing.AttributesDto
import com.example.sportz.domain.model.SportsDetails

data class DetailsDto(
    val attributes: AttributesDto,
    val id: Int,
    val type: String,
    val relationships: RelationshipsDto
) {
    fun toSportsDetails(): SportsDetails {
        return SportsDetails(
            id = id,
            name = attributes.name!!,
            description = attributes.description!!,
            image = relationships.images.data.first().url
        )
    }
}