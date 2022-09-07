package com.example.notbored

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast

class ScreenActivities : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activities_screen)

        val arrayAdapter: ArrayAdapter<*>

        val actividades = mutableListOf("Education","Recreational","Social","Diy","Charity","Cooking","Relaxation","Music","Busywork")
        val lvDatos = findViewById<ListView>(R.id.lvActividades)

        supportActionBar?.hide()

        arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, actividades)
        lvDatos.adapter = arrayAdapter
        lvDatos.setOnItemClickListener(){parent, view, position, id ->
            Toast.makeText(this, parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show()
            goToActivity(SuggestionActivity::class.java)
        }
    }
    private fun goToActivity(cls : Class<*>){
        val goToActivity = Intent(this,cls)
        startActivity(goToActivity)
    }
}