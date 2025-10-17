package com.example.qurio.presentation.animation

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.view.animation.OvershootInterpolator
import android.widget.ImageView
import com.example.qurio.R

@SuppressLint("ClickableViewAccessibility")
fun setupSwipeUpGesture(view: View, onSwipeComplete: () -> Unit) {
    val swipeImage = view.findViewById<ImageView>(R.id.swipe_image)
    var initialY = 0f
    var offsetY = 0f
    val swipeThreshold = 40f

    swipeImage.setOnTouchListener { v, event ->
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                initialY = v.y
                offsetY = v.y - event.rawY
                true
            }

            MotionEvent.ACTION_MOVE -> {
                val newY = event.rawY + offsetY
                if (newY in 0f..initialY) {
                    v.y = newY
                }
                true
            }

            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                val reachedTop = v.y <= swipeThreshold
                if (reachedTop) {
                    v.animateToTop(initialY, onSwipeComplete)
                } else {
                    v.animateToStart(initialY)
                }
                true
            }

            else -> false
        }
    }
}

private fun View.animateToTop(initialY: Float, onSwipeComplete: () -> Unit) {
    animate()
        .y(0f)
        .setDuration(150)
        .setInterpolator(DecelerateInterpolator())
        .withEndAction {
            onSwipeComplete()
            postDelayed({
                animateToStart(initialY)
            }, 200)
        }
        .start()
}

private fun View.animateToStart(initialY: Float) {
    animate()
        .y(initialY)
        .setDuration(300)
        .setInterpolator(OvershootInterpolator())
        .start()
}
