package com.example.sportz.data.remote.dto

data class AttributesDto(
    val decathlon_id: Int,
    val description: String,
    val icon: String?,
    val locale: String,
    val name: String?,
    val parent_id: Any,
    val slug: String,
    val weather: List<Any>
)