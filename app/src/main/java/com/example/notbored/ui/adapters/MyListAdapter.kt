package com.example.notbored.ui.adapters

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.annotation.SuppressLint
import com.example.notbored.R


/**
 * Adapter used for generate a custom layout in ListView
 * @see R.layout.custom_list
 *
 * */
class MyListAdapter(private val context: Activity, private val title: Array<String>) :
    ArrayAdapter<String>(context, R.layout.custom_list, title) {

    @SuppressLint("InflateParams", "ViewHolder")
    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.custom_list, null, true)

        val titleText = rowView.findViewById(R.id.title) as TextView

        titleText.text = title[position]

        return rowView
    }
}