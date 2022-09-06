package com.example.notbored

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class TermsAndConditions : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_terms_and_conditions)

        val closeIcon = findViewById<ImageView>(R.id.closeIcon)
        closeIcon.setOnClickListener {
            onBackPressed()
        }
    }
}