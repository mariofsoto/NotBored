package com.example.notbored

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.res.Resources
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.CheckBox
import android.widget.SeekBar
import android.widget.TextView

object Utils {

    fun customCheckBox(checkBox : CheckBox, function: () -> Unit) {
        val clickableSpan: ClickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                widget.cancelPendingInputEvents()
                function()
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = true
                ds.isFakeBoldText = true
            }
        }

        val partialLinkText = SpannableString("Agree on Terms and Conditions")
        partialLinkText.setSpan(clickableSpan, 9, partialLinkText.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        checkBox.text = partialLinkText
        checkBox.movementMethod = LinkMovementMethod.getInstance()
    }

    fun getSharedParticipants(context : Context) : Int{
        val boredPreferences = context.getSharedPreferences(BORED_PREFERENCES, MODE_PRIVATE)
        val editPrefs = boredPreferences.edit()
        val participants = boredPreferences.getInt(PARTICIPANTS,0)
        editPrefs.apply()
        return participants
    }

    fun getSharedPrice(context : Context) : Float{
        val boredPreferences = context.getSharedPreferences(BORED_PREFERENCES, MODE_PRIVATE)
        val editPrefs = boredPreferences.edit()
        val price = boredPreferences.getFloat(PRICE,0f)
        editPrefs.apply()
        return price
    }









}