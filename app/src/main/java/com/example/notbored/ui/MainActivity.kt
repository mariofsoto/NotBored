package com.example.notbored.ui

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.notbored.*
import com.example.notbored.databinding.ActivityMainBinding
import com.example.notbored.model.BoredPreferences
import com.example.notbored.utils.BORED_PREFERENCES
import com.example.notbored.utils.BoredValidation
import com.example.notbored.utils.TAG
import com.example.notbored.utils.Utils

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    /**
     * Shows and validates terms and conditions and input passed on EditText.
     * Can go to [ScreenActivities] or [TermsAndConditionsActivity].
     *
     * */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        validation {
            savePreferences()
            goToActivity(ScreenActivities::class.java)
        }
        Utils.customCheckBox(binding.termsCheckBox) {
            goToActivity(TermsAndConditionsActivity::class.java)
        }


    }


    /**
     * If input from EditText is a number and Checkbox is checked, executes the given function.
     * @param function executes only if terms are validated.
     * */
    private fun validation(function: () -> Unit) {
        val validation = BoredValidation(binding.startButton)
        validation.validateInputText(
            binding.participantsEditText,
            binding.participantValidationError
        )
        validation.validateTerms(
            binding.termsCheckBox,
            binding.termsValidationError,
            function
        )
    }

    /**
     * Starts given activity.
     * @param cls activity to go to.
     * */
    private fun goToActivity(cls: Class<*>) {
        val goToActivity = Intent(this, cls)
        startActivity(goToActivity)
    }


    /**
     * Saved every preference when executed. If EditText is empty, [saveParticipants] as -1.
     * @see Repository used preferences to be passed on to repository.
     * @sample BoredPreferences.PARTICIPANTS
     * @sample BoredPreferences.MAX_PRICE
     * @sample BoredPreferences.MIN_PRICE
     * */
    private fun savePreferences() {
        val participants = binding.participantsEditText.text.toString()
        val minPrice = binding.rangeSlider.values[0]
        val maxPrice = binding.rangeSlider.values[1]
        val boredPrefs: SharedPreferences = getSharedPreferences(BORED_PREFERENCES, MODE_PRIVATE)
        val editPreferences = boredPrefs.edit()

        if (participants.isNotBlank()) {
            editPreferences.saveParticipants(participants.toInt())
        } else editPreferences.saveParticipants(-1)

        editPreferences.putFloat(BoredPreferences.MIN_PRICE.value, minPrice)
        editPreferences.putFloat(BoredPreferences.MAX_PRICE.value, maxPrice)

        editPreferences.apply()
        Log.d(
            TAG, "saved price range ($minPrice , $maxPrice) and $participants into shared " +
                    "folder ${
                        boredPrefs
                            .all
                    }"
        )

    }


    private fun SharedPreferences.Editor.saveParticipants(int: Int) {
        this.putInt(BoredPreferences.PARTICIPANTS.value, int)
    }

}