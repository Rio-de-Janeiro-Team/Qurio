package com.example.qurio.presentation


import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel

fun setShapeBackground(): MaterialShapeDrawable {
    return MaterialShapeDrawable(
        ShapeAppearanceModel.builder().setTopLeftCorner(CornerFamily.CUT, 16F)
            .setBottomRightCorner(CornerFamily.CUT, 16F).build()
    ).apply {
        setStroke(0.5f, Color.parseColor("#0AFFFFFF"))
        fillColor = ColorStateList.valueOf(Color.parseColor("#0B0F14"))
    }
}


class StrokeTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    private val strokePaint = Paint().apply {
        style = Paint.Style.STROKE
        strokeWidth = 2f
    }

    override fun onDraw(canvas: Canvas) {
        paint.strokeWidth = strokePaint.strokeWidth
        paint.style = Paint.Style.STROKE
        paint.color = strokePaint.color
        super.onDraw(canvas)

        paint.color = currentTextColor
        super.onDraw(canvas)
    }
}
