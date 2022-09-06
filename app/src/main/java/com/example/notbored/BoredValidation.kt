package com.example.notbored

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged

class BoredValidation(private val button : Button) {

    private fun validateParticipants(editText : CharSequence?, errorText: TextView){
        when{
            editText.isNullOrEmpty() -> goodToGo(errorText)
            editText.toString().toIntOrNull() == null -> checkValidation(errorText)
            editText.toString().toInt() in 1..8 -> goodToGo(errorText)
            editText.toString().toInt() !in 1..8 ->checkValidation(errorText)
        }
    }


    fun validateInputText(input:EditText, error: TextView){
        input.doOnTextChanged { text, _, _, _ ->
            validateParticipants(text,error)
        }
    }

    fun validateTerms(checkBox : CheckBox, error: TextView, function : ()->Unit){
        button.setOnClickListener {
            if(!checkBox.isChecked) error.visibility = View.VISIBLE else function()
        }

        checkBox.setOnCheckedChangeListener { _, b ->
            if(b) error.visibility = View.GONE
        }

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