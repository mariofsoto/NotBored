package com.example.notbored

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.notbored.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        setContentView(binding.root)

        // TODO change Toast to activity with goToActivity()
        validation{
            Toast.makeText(this,"voy a la activity",Toast.LENGTH_SHORT)
                .show()
        }

        Utils.customCheckBox(binding.termsCheckBox){
            goToActivity(TermsAndConditionsActivity::class.java)
        }





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
}