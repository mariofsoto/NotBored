package com.example.notbored.service

import com.example.notbored.model.BoredEvent
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BoredApi {

    /**
     * @see BoredEvent data class to capture bored api data
     * @return desired [BoredEvent] depending on function parameters passed to function
     * @sample getBoredEvent returns a random activity
     * */
    @GET("activity")
    suspend fun getBoredEvent(
        @Query("type") type: String? = null,
        @Query("minprice") minPrice: Float? = null,
        @Query("maxprice") maxPrice: Float? = null,
        @Query("participants") participants: Int? = null,
    ): Response<BoredEvent>

}