package com.example.notbored.service

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.notbored.model.BoredEvent
import com.example.notbored.utils.TAG
import java.lang.Exception


/**
 * Class to handle api calls according to needs from view.
 * */
class Repository {
    private val api: BoredApi = RetrofitClient.getInstance().create(BoredApi::class.java)


    /**
     * Returns a [BoredEvent] from the api call as [LiveData] to be observed on main thread.
     * Function manages to make the specific api call according to value of [type],
     * [participants], [minPrice] and [maxPrice].
     * @param participants if is "-1" means that is participants number is random.
     * @param maxPrice if max and min price are 1 and 0 respectively, means that the
     * price is random.
     *
     * */
    fun getBoredEvent(type: String, participants: Int, minPrice: Float, maxPrice: Float):
            LiveData<BoredEvent> =
        liveData {
            val randomParticipants = participants < 0
            val randomPrice = minPrice == 0f && maxPrice == 1.0f
            val randomType = type == "random"

            try {
                val response = when {
                    randomType && randomPrice && randomParticipants -> api.getBoredEvent()
                    randomType && randomParticipants -> api.getBoredEvent(minPrice, maxPrice)
                    randomType && randomPrice -> api.getBoredEvent(participants)
                    randomType -> api.getBoredEvent(participants, minPrice, maxPrice)
                    randomPrice && randomParticipants -> api.getBoredEvent(type)
                    randomParticipants -> api.getBoredEvent(type, minPrice, maxPrice)
                    randomPrice -> api.getBoredEvent(type, participants)
                    else -> api.getBoredEvent(type, participants, minPrice, maxPrice)
                }

                if (response.isSuccessful) {
                    response.body()?.let {
                        emit(it)
                    }
                    Log.d(
                        TAG, "getBoredEvent with $type $participants price ($minPrice," +
                                "$maxPrice): $response"
                    )
                }

            } catch (e: Exception) {
                Log.d(TAG, "getBoredEvent Error: ${e.message}")
            }
        }
}