package com.example.qurio.presentation.view.base

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.qurio.R

@BindingAdapter("app:coinChangeText")
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

@BindingAdapter("app:streakText")
fun setStreakText(textView: TextView, streakNumber: Int) {
    val text = if (streakNumber == 0) {
        "0 day streak, start make a series"
    } else {
        "$streakNumber day streak, make a big series"
    }
    textView.text = text
}

@BindingAdapter(value = ["streakNumber", "threshold"])
fun showWhenStreakAbove(view: View, streakNumber: Int, threshold: Int) {
    view.visibility = if (streakNumber >= threshold) View.VISIBLE else View.GONE
}