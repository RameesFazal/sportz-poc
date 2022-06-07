package com.example.sportz.data.remote

import com.example.sportz.data.remote.dto.details.SportsDetailsDto
import com.example.sportz.data.remote.dto.listing.SportsDto
import retrofit2.http.GET
import retrofit2.http.Path

interface SportsApi {

    @GET("/sports")
    suspend fun getSports(): SportsDto

    @GET("/sports/{id}")
    suspend fun getSportsDetail(@Path("id") id: Int): SportsDetailsDto

    companion object {
        const val BASE_URL = "https://sports.api.decathlon.com"
    }
}