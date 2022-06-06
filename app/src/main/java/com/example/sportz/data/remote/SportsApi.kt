package com.example.sportz.data.remote

import com.example.sportz.data.remote.dto.SportsDto
import retrofit2.http.GET

interface SportsApi {

    @GET("/sports")
    suspend fun getSports() : SportsDto

    companion object {
        const val BASE_URL = "https://sports.api.decathlon.com"
    }
}