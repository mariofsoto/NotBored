package com.example.notbored

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BoredApi {

    @GET("api/activity/")
    suspend fun getRandomActivity() : Response<BoredEvent>

    @GET("api/activity")
    suspend fun getRandomWithParticipants(@Query("participants") participants : Int) :
            Response<BoredEvent>

    @GET("api/activity")
    suspend fun getSpecificActivity(@Query("type") type:String, @Query("participants")
    participants: Int) : Response<BoredEvent>


}