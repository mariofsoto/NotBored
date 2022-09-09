package com.example.notbored


import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.doOnTextChanged

class BoredValidation(private val button : Button) {

    /** Validates inputs passed on input. Shows an error message and disables button.
     * @param editText Validates input from an [EditText].
     * @param errorText is visible only when validation is needed.
     * @see validateInputText
     * */
    private fun validateParticipants(editText : CharSequence?, errorText: TextView){
        when{
            editText.isNullOrEmpty() -> goodToGo(errorText)
            editText.toString().toIntOrNull() == null -> checkValidation(errorText)
            editText.toString().toInt() in 1..8 -> goodToGo(errorText)
            editText.toString().toInt() !in 1..8 ->checkValidation(errorText)
        }
    }


    /**
     * Whenever input text is changed, executes validation.
     * @see validateParticipants
     * */
    fun validateInputText(input:EditText, error: TextView){
        input.doOnTextChanged { text, _, _, _ ->
            validateParticipants(text,error)
        }
    }

    /**
    * Validate terms and conditions and executes a function passed when checkbox is checked.
     * @param error shows error text when checkbox is not checked
     * @param function executes a function when validation is OK.
    *
    * */
    fun validateTerms(checkBox : CheckBox, error: TextView, function : ()->Unit){
        button.setOnClickListener {
            if(!checkBox.isChecked) error.visibility = View.VISIBLE else function()
        }

        checkBox.setOnCheckedChangeListener { _, b ->
            if(b) error.visibility = View.GONE
        }

    }

    /**
     * Enables button on main activity and makes invisible any related error text.
     * */
    private fun goodToGo(errorText: TextView){
        button.isEnabled = true
        errorText.visibility = View.INVISIBLE
    }


    /**
     * Disables button on main activity and makes visible any error relared text.
     * */
    private fun checkValidation(errorText: TextView){
        button.isEnabled = false
        errorText.visibility = View.VISIBLE
    }

}