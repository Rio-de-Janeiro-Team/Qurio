package com.example.qurio.presentation.view.base

import android.widget.TextView
import com.example.qurio.R

fun setCoinChangeColor(textView: TextView, coinChange: Int) {
    val context = textView.context
    val colorRes = if (coinChange >= 0) {
        R.color.shade_primary
    } else {
        R.color.red
    }
    val color = context.getColor(colorRes)
    textView.setTextColor(color)
}

