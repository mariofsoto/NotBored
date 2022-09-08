package com.example.notbored

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.example.notbored.databinding.ActivitySuggestionBinding

class SuggestionActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySuggestionBinding
    private val repository = Repository()
    private var isRandom = false
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
        isRandom = type == "random"

        observeEvent(type.lowercase(),participants,price)

        binding.topAppBar.title = type



        binding.btnRetry.setOnClickListener {
            setLayoutVisibility(false)
            observeEvent(type.lowercase(),participants,price)
        }
    }


    private fun setLayoutVisibility(visible: Boolean) {
        binding.progressBar.visibility = if(visible) View.GONE else View.VISIBLE
        binding.SuggestionLayout.visibility = if (visible) View.VISIBLE else View.GONE

    }

    private fun updateViews(binding: ActivitySuggestionBinding, event : BoredEvent){
        setLayoutVisibility(true)
        binding.tvActivityLabel.text = event.activity
        binding.tvParticipantsCount.text = event.participants.toString()
        binding.tvPrice.text = getPrice(event.price)
        if(isRandom){
            binding.tvCategory.visibility =  View.VISIBLE
            binding.tvCategory.text =  event.type
        }

    }

    private fun observeEvent(type:String, participants:Int){
        val event = repository.getBoredEvent(type,participants)
        event.observe(this){
            updateViews(binding,it)
        }
    }
    private fun observeEvent(type:String, participants:Int, price : Float){
        val event = when (type) {
            "random" -> repository.getBoredEvent(participants, price)
            else -> repository.getBoredEvent(type,participants,price)
        }
        event.observe(this){
            updateViews(binding,it)
        }
    }
    private fun getPrice(price: Float): String = when {
        price == 0.0f -> "Free"
        price > 0.0f && price <= 0.3f  -> "Low"
        price > 0.3f && price <= 0.6f  -> "Medium"
        else -> "High"
    }
}
