package com.example.qurio.presentation.view.components

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Shader
import android.util.AttributeSet
import android.view.View
import kotlin.math.min

class TimerProgressBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var maxTime: Int = 100
    private var currentTime: Int = 0
    private var animator: ValueAnimator? = null

    private val orangePaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.rgb(220, 107, 43)
    }

    val orangeShadowPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        shader = LinearGradient(
            0f, -50f, 0f, 30f,
            Color.WHITE, Color.TRANSPARENT,
            Shader.TileMode.CLAMP
        )
        style = Paint.Style.FILL
    }

    private val blackPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.rgb(11, 15, 14)
    }

    private val textPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE
        textAlign = Paint.Align.CENTER

    }

    val verticalBorderPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 10f
        color = Color.argb(20, 255, 255, 255)
    }

    fun setMaxTime(max: Int) {
        this.maxTime = max
        invalidate()
    }

    fun setCurrentTime(time: Int, animate: Boolean = true) {
        val clampedTime = time.coerceIn(0, maxTime)
        if (animate) {
            animator?.cancel()
            animator = ValueAnimator.ofInt(currentTime, clampedTime).apply {
                duration = min(500L, (kotlin.math.abs(clampedTime - currentTime) * 10L))
                addUpdateListener {
                    currentTime = it.animatedValue as Int
                    invalidate()
                }
                start()
            }
        } else {
            currentTime = clampedTime
            invalidate()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var width = MeasureSpec.getSize(widthMeasureSpec)
        var height = MeasureSpec.getSize(heightMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val ratio = 328f / 30f

        if (widthMode != MeasureSpec.EXACTLY && heightMode == MeasureSpec.EXACTLY) {
            width = (height * ratio).toInt()
        } else if (heightMode != MeasureSpec.EXACTLY && widthMode == MeasureSpec.EXACTLY) {
            height = (width / ratio).toInt()
        } else if (widthMode != MeasureSpec.EXACTLY) {
            width = 328
            height = 20
        }

        setMeasuredDimension(width, height)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        textPaint.textSize = height * 0.7f // Adjust text size to fit height
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val widthFloat = width.toFloat()
        val heightFloat = height.toFloat()

        val blackBackPath = Path().apply {

            moveTo(widthFloat * .05f, 0f) // left 1
            lineTo(widthFloat * .95f, 0f) // right 1
            lineTo(widthFloat * .98f, heightFloat * .27f) // right 2
            lineTo(widthFloat * .88f, heightFloat) // right 3
            lineTo(widthFloat * .12f, heightFloat) // left 3
            lineTo(widthFloat * .02f, heightFloat * .27f)  // left2
            lineTo(widthFloat * .05f, 0f) // left 1 again
            close()
        }
        // canvas.drawPath(blackBackPath, horizontalBorderPaint) // orange border

        canvas.drawPath(blackBackPath, blackPaint) // black background
        canvas.drawPath(blackBackPath, verticalBorderPaint)

        val leftOrangePath = Path().apply {

            moveTo(widthFloat * .05f, 0f) // left 1
            lineTo(widthFloat * .05f + 20f, 0f) // left 1 + 20
            lineTo(widthFloat * .02f + 20f, heightFloat * .27f) // left 2 + 20
            lineTo(widthFloat * .12f + 20f, heightFloat) // left 3 + 20
            lineTo(widthFloat * .12f, heightFloat) // left 3
            lineTo(widthFloat * .02f, heightFloat * .27f)  // left2
            lineTo(widthFloat * .05f, 0f) // left 1 again
            close()
        }
        canvas.drawPath(leftOrangePath, orangePaint)

        val rightOrangePath = Path().apply {

            moveTo(widthFloat * .95f, 0f) // right 1
            lineTo(widthFloat * .95f - 20f, 0f) // right 1 - 20
            lineTo(widthFloat * .98f - 20f, heightFloat * .27f) // right 2 - 20
            lineTo(widthFloat * .88f - 20f, heightFloat) // right 3 - 20
            lineTo(widthFloat * .88f, heightFloat) // right 3
            lineTo(widthFloat * .98f, heightFloat * .27f)  // right 2
            lineTo(widthFloat * .95f, 0f) // right 1 again
            close()
        }
        canvas.drawPath(rightOrangePath, orangePaint)


        val progWidth = widthFloat * (currentTime.toFloat() / maxTime.toFloat())

        val orangePath = Path().apply {
            val leftX1 = widthFloat * 0.05f + 19f
            val leftX2 = widthFloat * 0.02f + 32f
            val leftX3 = widthFloat * 0.12f + 20f

            val x1ToX2 = leftX1 - leftX2
            val x2ToX3 = leftX3 - leftX2

            moveTo(leftX1, 10f)

            val minRightX1 = widthFloat * 0.05f + 19f
            val minRightX2 = widthFloat * 0.02f + 32f
            val minRightX3 = widthFloat * 0.12f + 20f

            val calcRightX1 = progWidth * 0.95f - 19f
            val calcRightX2 = calcRightX1 + x1ToX2
            val calcRightX3 = calcRightX2 - x2ToX3

            val rightX1 = calcRightX1.coerceAtLeast(minRightX1)
            val rightX2 = calcRightX2.coerceAtLeast(minRightX2)
            val rightX3 = calcRightX3.coerceAtLeast(minRightX3)

            lineTo(rightX1, 10f)
            lineTo(rightX2, heightFloat * 0.25f + 1f)
            lineTo(rightX3, heightFloat - 10f)

            lineTo(leftX3, heightFloat - 10f)
            lineTo(leftX2, heightFloat * 0.25f + 1f)
            lineTo(leftX1, 10f)

            close()
        }
        canvas.drawPath(orangePath, orangePaint)
        canvas.drawPath(orangePath, orangeShadowPaint)

        // Draw text centered
        val text = "$currentTime Sec"
        val textX = widthFloat / 2f
        val textY =
            heightFloat / 2f - (textPaint.fontMetrics.ascent + textPaint.fontMetrics.descent) / 2f
        canvas.drawText(text, textX, textY, textPaint)

    }

}