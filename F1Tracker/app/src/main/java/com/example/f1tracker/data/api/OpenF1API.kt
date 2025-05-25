package com.example.f1tracker.data.api

import com.example.f1tracker.model.DriverDto
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenF1Api {
    @GET("drivers")
    suspend fun getDrivers(
        @Query("session_key") sessionKey: String = "latest"
    ): List<DriverDto>
}
