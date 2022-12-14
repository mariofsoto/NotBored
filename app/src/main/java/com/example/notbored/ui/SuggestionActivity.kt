package com.example.notbored.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.notbored.model.BoredEvent
import com.example.notbored.model.BoredPreferences
import com.example.notbored.databinding.ActivitySuggestionBinding
import com.example.notbored.service.Repository
import com.example.notbored.utils.TAG
import com.example.notbored.utils.Utils
import java.util.*

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

        val participants = Utils.getSharedValue(this, BoredPreferences.PARTICIPANTS) as Int
        val maxPrice = Utils.getSharedValue(this, BoredPreferences.MAX_PRICE) as Float
        val minPrice = Utils.getSharedValue(this, BoredPreferences.MIN_PRICE) as Float

        val dataFromLastActivity = intent.extras
        val type = dataFromLastActivity?.getString("type", "") ?: ""
        isRandom = type == "random"

        observeEvent(type.lowercase(), participants, minPrice, maxPrice)


        binding.topAppBar.title = type.boredCapitalize()

        binding.btnRetry.setOnClickListener {
            setLayoutVisibility(false)
            observeEvent(type.lowercase(), participants, minPrice, maxPrice)
        }
    }

    /**
     * Function that sets progressBar and main layout visibility
     * @param visible if true the layout is set to visible and the progressBar is set to gone
     * else shows the progress bar and hide the layout
     **/
    private fun setLayoutVisibility(visible: Boolean) {
        binding.progressBar.visibility = if (visible) View.GONE else View.VISIBLE
        binding.SuggestionLayout.visibility = if (visible) View.VISIBLE else View.GONE

    }
    /**
     * Updates visibility and wording according to load state, error and api result.
     * */
    private fun updateViews(binding: ActivitySuggestionBinding, event: BoredEvent) {

        setLayoutVisibility(true)
        if (event.error != null) {
            binding.errorActivityText.visibility = View.VISIBLE
            binding.SuggestionLayout.visibility = View.GONE

        } else {
            binding.tvActivityLabel.text = event.activity
            binding.tvParticipantsCount.text = event.participants.toString()
            binding.tvPrice.text = getPrice(event.price)
            if (isRandom) {
                binding.tvCategory.visibility = View.VISIBLE
                binding.tvCategory.text = event.type.boredCapitalize()
            }
        }


    }


    /**
     * Function that is called on main thread to observe api call.
     * Calls [updateViews] passing the result of the observed event.
     * @see updateViews
     * */
    private fun observeEvent(type: String, participants: Int, minPrice: Float, maxPrice: Float) {
        val event = repository.getBoredEvent(type, participants, minPrice, maxPrice)
        event.observe(this) {
            updateViews(binding, it)
        }
    }

    /**
     * Function that returns the price according to the following scale
    Free: the value is zero.
    Low: The value is greater than zero and less than or equal to 0.3
    Medium: the value is greater than 0.6 and less than or equal to 0.6
    High: the value is greater than 0.6
     * @param price of type float
     * @return string with the price according to the scale
     **/
    private fun getPrice(price: Float): String = when {
        price == 0.0f -> "Free"
        price > 0.0f && price <= 0.3f -> "Low"
        price > 0.3f && price <= 0.6f -> "Medium"
        else -> "High"
    }

    /**
     * Function extension of [String] to capitalize wording.
     * */
    private fun String.boredCapitalize(): String {
        return this.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
    }
}
