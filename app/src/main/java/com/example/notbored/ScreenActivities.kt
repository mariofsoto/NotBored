package com.example.notbored

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.notbored.databinding.ActivitiesScreenBinding

class ScreenActivities : AppCompatActivity() {

    private lateinit var binding: ActivitiesScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitiesScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val activities = arrayOf(
            "Education",
            "Recreational",
            "Social",
            "Diy",
            "Charity",
            "Cooking",
            "Relaxation",
            "Music",
            "Busywork"
        )

        val arrayAdapter = MyListAdapter(this, activities)

        binding.lvActivities.adapter = arrayAdapter

        binding.lvActivities.setOnItemClickListener { parent, _, position, _ ->
            goToActivity(
                SuggestionActivity::class.java,
                parent.getItemAtPosition(position).toString()
            )
        }

        binding.topAppBarActivities.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.randomCategory -> {
                    goToActivity(SuggestionActivity::class.java, "random")
                    true
                }
                else -> false
            }
        }

    }

    private fun goToActivity(cls: Class<*>, type: String) {
        val goToActivity = Intent(this, cls)
            .putExtra("type", type)
        startActivity(goToActivity)
    }

}