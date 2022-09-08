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

    fun getBoredEvent() : LiveData<BoredEvent> =
        liveData {
            try {
                val response = api.getBoredEvent()
                if(response.isSuccessful){
                    response.body()?.let { emit(it) }
                }

            }catch (e: Exception){
                Log.d(TAG, "getBoredEvent Error: ${e.message}")
            }
        }
    fun getBoredEvent(type:String) : LiveData<BoredEvent> =
        liveData {
            try {
                val response = api.getBoredEvent(type)
                if(response.isSuccessful){
                    response.body()?.let { emit(it) }
                }

            }catch (e: Exception){
                Log.d(TAG, "getBoredEvent Error: ${e.message}")
            }
        }
    fun getBoredEvent(participants: Int) : LiveData<BoredEvent> =
        liveData {
            try {
                val response = when {
                    participants < 0 ->api.getBoredEvent()
                    else ->api.getBoredEvent(participants)
                }
                if(response.isSuccessful){
                    response.body()?.let { emit(it) }
                }

            }catch (e: Exception){
                Log.d(TAG, "getBoredEvent Error: ${e.message}")
            }
        }
    fun getBoredEvent(type : String, participants : Int) : LiveData<BoredEvent> =
        liveData {
            try {
                val response = when {
                    participants < 0 -> api.getBoredEvent(type)
                    else -> api.getBoredEvent(type,participants)
                }
                if(response.isSuccessful){
                    response.body()?.let { emit(it) }
                    Log.d(TAG, "getBoredEvent: $response")
                }

            }catch (e: Exception){
                Log.d(TAG, "getBoredEvent Error: ${e.message}")
            }
        }
    fun getBoredEvent(type: String, participants: Int, price : Float) : LiveData<BoredEvent> =
        liveData {
            try {
                val response = when{
                    type == "random" -> api.getBoredEvent(participants,price)
                    price < 0f && participants < 0 -> api.getBoredEvent(type)
                    participants < 0 -> api.getBoredEvent(type,price)
                    price < 0f -> api.getBoredEvent(type,participants)
                    else -> api.getBoredEvent(type,participants,price)
                }

                if(response.isSuccessful){
                    response.body()?.let { emit(it) }
                    Log.d(TAG, "getBoredEvent with $type $participants $price: ${response}")
                }

            }catch (e: Exception){
                Log.d(TAG, "getBoredEvent Error: ${e.message}")
            }
        }
    fun getBoredEvent(type : String, price: Float) : LiveData<BoredEvent> =
        liveData {
            try {
                val response = when{
                    price < 0f -> api.getBoredEvent(type)
                    else -> api.getBoredEvent(type, price)
                }
                if(response.isSuccessful){
                    response.body()?.let { emit(it) }
                }

            }catch (e: Exception){
                Log.d(TAG, "getBoredEvent Error: ${e.message}")
            }
        }
    fun getBoredEvent(participants: Int, price: Float) : LiveData<BoredEvent> =
        liveData {
            try {
                val response = when {
                    price < 0f && participants < 0 -> api.getBoredEvent()
                    price < 0f -> api.getBoredEvent(participants)
                    participants < 0 -> api.getBoredEvent(price)
                    else -> api.getBoredEvent(participants, price)
                }
                if(response.isSuccessful){
                    response.body()?.let { emit(it) }
                }

            }catch (e: Exception){
                Log.d(TAG, "getBoredEvent Error: ${e.message}")
            }
        }
}