package com.example.notbored

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.notbored.databinding.ActivityMainBinding

const val TAG = "kevin"

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // TODO change Toast to intent activity with goToActivity() function
        validation{
            Toast.makeText(this,
                "Going to next activity",
                Toast.LENGTH_SHORT).show()
            savePreferences()
        }
        Utils.customCheckBox(binding.termsCheckBox){
            goToActivity(TermsAndConditionsActivity::class.java)
        }
        changeSeekBarText(binding.seekBar)







    }


    private fun changeSeekBarText(seekBar: SeekBar){
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                updateSeekBarText(binding.priceBar,p1)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        })
    }

    private fun updateSeekBarText(text : TextView, number : Int){
        val formattedNumber = getFormattedPriceRange(number)
        when{
            formattedNumber < 0 ->text.text = getString(R.string.seekbar_random_price)
            else -> text.text = getString(R.string.seekbar_text,formattedNumber.toString())
        }
    }

    private fun getFormattedPriceRange(priceRange : Int): Float{
        return ((priceRange-1).toFloat()/10f)
    }




    private fun validation(function:()-> Unit){
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

    private fun goToActivity(cls : Class<*>){
        val goToActivity = Intent(this,cls)
        startActivity(goToActivity)
    }

    private fun savePreferences(){

        val participants = binding.participantsEditText.text.toString()
        val priceRange = getFormattedPriceRange(binding.seekBar.progress)
        val boredPrefs : SharedPreferences = getSharedPreferences("bored", MODE_PRIVATE)
        val editPreferences = boredPrefs.edit()
        if(participants.isNotBlank())editPreferences.saveParticipants(participants.toInt())
        else editPreferences.saveParticipants(-1)
        if(priceRange in 0f..1f) editPreferences.savePrice(priceRange)
        else editPreferences.savePrice(-1f)

        editPreferences.apply()
        Log.d(TAG, "saved $priceRange and $participants into shared folder ${boredPrefs.all}")

    }

    private fun SharedPreferences.Editor.saveParticipants(int: Int){
        this.putInt("participants",int)
    }
    private fun SharedPreferences.Editor.savePrice(float: Float){
        this.putFloat("price",float)
    }

}