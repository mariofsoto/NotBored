package com.example.notbored

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)



        val startButton = findViewById<Button>(R.id.startButton)
        val validation = BoredValidation(startButton)
        val validationError = findViewById<TextView>(R.id.participantValidationError)
        val termsAndConditionLink = findViewById<TextView>(R.id.termsConditionsLink)
        termsAndConditionLink.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        val participantsInput = findViewById<EditText>(R.id.participantsEditText)
        val termsCheckBox = findViewById<CheckBox>(R.id.termsCheckBox)
        val termsError = findViewById<TextView>(R.id.termsValidationError)
        val goToTermsAndConditions = Intent(this,
            TermsAndConditions::class.java)

        val clickableSpan: ClickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                widget.cancelPendingInputEvents()
                startActivity(goToTermsAndConditions)
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = false
                ds.isFakeBoldText = true
            }
        }

        val linkText = SpannableString("Terms and Conditions")
        linkText.setSpan(clickableSpan, 0, linkText.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        val cs = TextUtils.expandTemplate(
            "Agree on ^1", linkText
        )

        termsCheckBox.text = cs
        termsCheckBox.movementMethod = LinkMovementMethod.getInstance();




        participantsInput.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                validation.validateParticipants(p0,validationError)
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })

        termsAndConditionLink.setOnClickListener {
            startActivity(goToTermsAndConditions)
        }

        startButton.setOnClickListener {
            if(!termsCheckBox.isChecked)termsError.visibility = View.VISIBLE else Log.d(
                "kevin",
                "onCreate: go to actividades screen "
            )
        }

        termsCheckBox.setOnCheckedChangeListener { _, b ->
            if(b) termsError.visibility = View.GONE
        }


    }
}