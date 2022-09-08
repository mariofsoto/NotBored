package com.example.notbored

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.example.notbored.databinding.ActivitySuggestionBinding

class SuggestionActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySuggestionBinding
    private val repository = Repository()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySuggestionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.topAppBar.setNavigationOnClickListener {
            onBackPressed()
        }

        val participants = Utils.getSharedParticipants(this)
        val price = Utils.getSharedPrice(this)
        val dataFromLastActivity = intent.extras
        val type =  dataFromLastActivity?.getString("type","") ?: ""

        binding.topAppBar.title = type

        observeEvent(type.lowercase(),participants,price)

        binding.btnRetry.setOnClickListener {
            observeEvent(type.lowercase(),participants)
        }
    }
    private fun updateViews(binding: ActivitySuggestionBinding, event : BoredEvent){
        binding.tvActivityLabel.text = event.activity
        binding.tvParticipantsCount.text = event.participants.toString()
        binding.tvPrice.text = event.price.toString()
    }

    private fun observeEvent(type:String, participants:Int){
        val event = repository.getBoredEvent(type,participants)
        event.observe(this){
            updateViews(binding,it)
        }
    }
    private fun observeEvent(type:String, participants:Int, price : Float){
        val event = repository.getBoredEvent(type,participants,price)
        event.observe(this){
            updateViews(binding,it)
        }
    }
}
