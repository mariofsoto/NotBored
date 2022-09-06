package com.example.notbored

import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView

class BoredValidation(val button : Button) {

    fun validateParticipants(editText : CharSequence?, errorText: TextView){
        when{
            editText.isNullOrEmpty() -> goodToGo(errorText)
            editText.toString().toIntOrNull() == null -> goodToGo(errorText)
            editText.toString().toInt() in 1..8 -> goodToGo(errorText)
            editText.toString().toInt() !in 1..8 ->checkValidation(errorText)
        }
    }

    fun validateTerms(){

    }

    private fun goodToGo(errorText: TextView){
        button.isEnabled = true
        errorText.visibility = View.INVISIBLE
    }

    private fun checkValidation(errorText: TextView){
        button.isEnabled = false
        errorText.visibility = View.VISIBLE
    }

}