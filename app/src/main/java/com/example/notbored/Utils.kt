package com.example.notbored

import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.CheckBox

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



}