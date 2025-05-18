package com.example.f1tracker.data.repository

import com.example.f1tracker.data.api.OpenF1Api
import com.example.f1tracker.model.DriverDto
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object F1Repository {
    private val api: OpenF1Api = Retrofit.Builder()
        .baseUrl("https://api.openf1.org/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(OpenF1Api::class.java)

    suspend fun getDrivers(): List<DriverDto> {
        return api.getDrivers()
    }
}