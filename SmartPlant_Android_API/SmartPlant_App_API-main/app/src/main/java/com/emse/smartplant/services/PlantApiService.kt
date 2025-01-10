package com.emse.smartplant.services

import com.emse.smartplant.models.PlantCommand
import com.emse.smartplant.models.PlantDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PlantApiService {

    @GET("plants")
    fun findAll(): Call<List<PlantDto>>

    @GET("plants/{id}")
    fun findById(@Path("id") id: Long): Call<PlantDto>

    @PUT("plants/{id}")
    fun updatePlant(@Path("id") id: Long, @Body plant: PlantCommand): Call<PlantDto>

    @POST("api/plants")
    fun createPlant(@Body plant: PlantCommand): Call<PlantDto>

    @DELETE("api/plants/{id}")
    fun deletePlant(@Path("id") plantId: Long): Call<Void>


}