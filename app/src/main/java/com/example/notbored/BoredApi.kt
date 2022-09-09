package com.example.notbored

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BoredApi {

    @GET("activity")
    suspend fun getBoredEvent() : Response<BoredEvent>

    @GET("activity")
    suspend fun getBoredEvent(@Query("type") type : String) : Response<BoredEvent>

    @GET("activity")
    suspend fun getBoredEvent(@Query("minprice") minPrice : Float, @Query("maxprice") maxPrice :
    Float ) : Response<BoredEvent>

    @GET("activity")
    suspend fun getBoredEvent(@Query("participants") participants : Int) : Response<BoredEvent>

    @GET("activity")
    suspend fun getBoredEvent(@Query("type") type:String, @Query("participants")
    participants: Int) : Response<BoredEvent>

    @GET("activity")
    suspend fun getBoredEvent(@Query("type") type:String, @Query("participants")
    participants: Int, @Query("minprice") minPrice : Float,@Query("maxprice") maxPrice : Float) :
            Response<BoredEvent>

    @GET("activity")
    suspend fun getBoredEvent(@Query("participants")
    participants: Int, @Query("minprice") minPrice : Float,@Query("maxprice") maxPrice : Float) :
            Response<BoredEvent>

    @GET("activity")
    suspend fun getBoredEvent(@Query("type") type: String, @Query("minprice") minPrice : Float,
                              @Query("maxprice") maxPrice : Float) : Response<BoredEvent>



}