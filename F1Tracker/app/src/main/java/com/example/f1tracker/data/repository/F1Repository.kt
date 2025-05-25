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
    object FlagUtil {
        fun part1() = "CTF{"
        fun part2() = "split_"
        fun part3() = "flag}"

        fun getFlag() = part1() + part2() + part3()
    }
    fun getDriverData(): String {
        // Dummy method to keep the class realistic
        return "Fetching driver data..."
    }

    // Hidden flag parts
    private fun calculateSpeed() = "CTF{"
    private fun getTrackTime() = "speed_"
    private fun getTelemetry() = "timing_key}"

    // The real flag method
    fun revealTelemetryData(): String {
        return calculateSpeed() + getTrackTime() + getTelemetry()
    }


    suspend fun getDrivers(): List<DriverDto> {
        return api.getDrivers()
    }
}