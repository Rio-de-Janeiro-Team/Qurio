import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageView
import com.example.qurio.R

fun swipeUpAnimation(view: View) {
    val arrow = view.findViewById<ImageView>(R.id.arrow1)

    val handler = Handler(Looper.getMainLooper())

    fun createArrowInstance(): ImageView {
        val newArrow = ImageView(view.context)
        newArrow.setImageResource(R.drawable.ic_arrow_up)
        newArrow.layoutParams = arrow.layoutParams
        newArrow.alpha = 0f
        newArrow.translationY = arrow.translationY
        (arrow.parent as? ViewGroup)?.addView(newArrow)
        return newArrow
    }

    fun animateArrow(instance: ImageView) {
        val moveUp = ObjectAnimator.ofFloat(instance, View.TRANSLATION_Y, 300f, -140f)
        val fade = ObjectAnimator.ofFloat(instance, View.ALPHA, 0f, 0.3f, 0.7f, 1f, 0.5f, 0f)

        AnimatorSet().apply {
            playTogether(moveUp, fade)
            duration = 3000
            interpolator = AccelerateDecelerateInterpolator()

            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    (instance.parent as? ViewGroup)?.removeView(instance)
                }
            })

            start()
        }
    }

    fun startWaveAnimation() {
        fun loop() {
            val a1 = createArrowInstance()
            animateArrow(a1)
            handler.postDelayed({
                val a2 = createArrowInstance()
                animateArrow(a2)
            }, 200)
            handler.postDelayed({
                val a3 = createArrowInstance()
                animateArrow(a3)
            }, 400)
            handler.postDelayed({ loop() }, 2500)
        }
        loop()
    }

    startWaveAnimation()
}
