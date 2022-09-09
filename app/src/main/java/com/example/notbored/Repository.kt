package com.example.notbored

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception

class Repository {
    private val api: BoredApi = RetrofitClient.getInstance().create(BoredApi::class.java)

    fun getBoredEvent(type: String, participants: Int, minPrice : Float, maxPrice : Float) :
            LiveData<BoredEvent> =
        liveData {
            val randomParticipants = participants < 0
            val randomPrice = minPrice == 0f && maxPrice == 1.0f
            val randomType = type == "random"

            try {
                val response = when{
                    randomType && randomPrice && randomParticipants  -> api.getBoredEvent()
                    randomType && randomParticipants  -> api.getBoredEvent(minPrice,maxPrice)
                    randomType && randomPrice -> api.getBoredEvent(participants)
                    randomType  -> api.getBoredEvent(participants,minPrice,maxPrice)
                    randomPrice && randomParticipants -> api.getBoredEvent(type)
                    randomParticipants -> api.getBoredEvent(type,minPrice,maxPrice)
                    randomPrice -> api.getBoredEvent(type,participants)
                    else -> api.getBoredEvent(type,participants,minPrice,maxPrice)
                }

                if(response.isSuccessful){
                    response.body()?.let { emit(it) }
                    Log.d(TAG, "getBoredEvent with $type $participants price ($minPrice," +
                            "$maxPrice): $response")
                }

            }catch (e: Exception){
                Log.d(TAG, "getBoredEvent Error: ${e.message}")
            }
        }
}