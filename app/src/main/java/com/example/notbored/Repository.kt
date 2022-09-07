package com.example.notbored

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception

class Repository {
    private val api: BoredApi = RetrofitClient.getInstance().create(BoredApi::class.java)

    fun getRandomActivity(){
        GlobalScope.launch(Dispatchers.IO){
            try {
                val response = api.getRandomActivity()
                if(response.isSuccessful) Log.d(TAG, response.body().toString())
            }catch (e: Exception){
                Log.d(TAG, "onCreate: ${e.message}")
            }

        }
    }

    fun getSpecificActivity(type:String,participants:Int){
        GlobalScope.launch(Dispatchers.IO){
            try {
                val response = api.getSpecificActivity(type,participants)
                if(response.isSuccessful) Log.d(TAG, response.body().toString())
            }catch (e: Exception){
                Log.d(TAG, "onCreate: ${e.message}")
            }

        }
    }

    fun getRandomWithParticipants(participants: Int){
        GlobalScope.launch(Dispatchers.IO){
            try {
                val response = api.getRandomWithParticipants(participants)
                if(response.isSuccessful) Log.d(TAG, response.body().toString())
            }catch (e: Exception){
                Log.d(TAG, "onCreate: ${e.message}")
            }

        }
    }


}