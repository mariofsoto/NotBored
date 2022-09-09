package com.example.notbored

import MyListAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import com.example.notbored.databinding.ActivitiesScreenBinding

class ScreenActivities : AppCompatActivity() {
    private lateinit var binding: ActivitiesScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitiesScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val actividades = arrayOf<String>("Education","Recreational","Social","Diy","Charity","Cooking","Relaxation","Music","Busywork")

        val arrayAdapter = MyListAdapter(this,actividades)

        //arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, actividades)

        binding.lvActivities.adapter = arrayAdapter
        binding.lvActivities.setOnItemClickListener(){parent, _, position, _ ->
            Toast.makeText(this, parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show()


            goToActivity(SuggestionActivity::class.java, parent.getItemAtPosition(position).toString())
        }
        binding.topAppBarActivities.setOnMenuItemClickListener {menuItem ->
            when (menuItem.itemId) {
                R.id.randomCategory -> {
                    goToActivity(SuggestionActivity::class.java,"random")
                    true

                }
                else -> false
            }
        }





    }
    private fun goToActivity(cls : Class<*>, type : String){
        val goToActivity = Intent(this,cls)
            .putExtra("type",type)
        startActivity(goToActivity)
    }
}