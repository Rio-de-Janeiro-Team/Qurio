package com.example.qurio.presentation.view.components

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.example.qurio.R
import kotlin.math.abs

class StyledTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    private val outlinePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val shadowPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var outlineColor: Int = Color.TRANSPARENT
    private var outlineWidth: Float = 0f

    private var gradientStartColor: Int = Color.TRANSPARENT
    private var gradientEndColor: Int = Color.TRANSPARENT
    private var gradientAngle: Int = 90
    private var useGradient: Boolean = false

    private var shadowColor: Int = Color.TRANSPARENT
    private var shadowRadius: Float = 0f
    private var shadowDx: Float = 0f
    private var shadowDy: Float = 0f
    private var useShadow: Boolean = false

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.OutlineTextView,
            0, 0
        ).apply {
            try {
                outlineColor = getColor(R.styleable.OutlineTextView_outlineColor, Color.TRANSPARENT)
                outlineWidth = getDimension(R.styleable.OutlineTextView_outlineWidth, 0f)

                gradientStartColor = getColor(
                    R.styleable.OutlineTextView_gradientStartColor,
                    Color.TRANSPARENT
                )
                gradientEndColor = getColor(
                    R.styleable.OutlineTextView_gradientEndColor,
                    Color.TRANSPARENT
                )
                gradientAngle = getInt(R.styleable.OutlineTextView_gradientAngle, 90)
                useGradient = gradientStartColor != Color.TRANSPARENT &&
                        gradientEndColor != Color.TRANSPARENT

                shadowColor = getColor(R.styleable.OutlineTextView_shadowColor, Color.TRANSPARENT)
                shadowRadius = getDimension(R.styleable.OutlineTextView_shadowRadius, 0f)
                shadowDx = getDimension(R.styleable.OutlineTextView_shadowDx, 0f)
                shadowDy = getDimension(R.styleable.OutlineTextView_shadowDy, 0f)
                useShadow = shadowColor != Color.TRANSPARENT && shadowRadius > 0
            } finally {
                recycle()
            }
        }

        setupPaints()
        adjustPaddingForEffects()
        setLayerType(LAYER_TYPE_SOFTWARE, null)
    }


    private fun setupPaints() {
        outlinePaint.apply {
            style = Paint.Style.STROKE
            strokeWidth = outlineWidth
            color = outlineColor
        }

        if (useShadow) {
            shadowPaint.apply {
                style = Paint.Style.FILL
                color = currentTextColor
                setShadowLayer(shadowRadius, shadowDx, shadowDy, shadowColor)
            }
        }
    }

    private fun adjustPaddingForEffects() {
        val paddingIncrease = (outlineWidth + shadowRadius +
                maxOf(abs(shadowDx), abs(shadowDy))).toInt()

        setPadding(
            paddingLeft + paddingIncrease,
            paddingTop + paddingIncrease,
            paddingRight + paddingIncrease,
            paddingBottom + paddingIncrease
        )
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        val textStr = text?.toString() ?: return
        val baseX = paddingLeft.toFloat()
        val baseY = baseline.toFloat()

        outlinePaint.textSize = textSize
        outlinePaint.typeface = typeface
        shadowPaint.textSize = textSize
        shadowPaint.typeface = typeface

        if (useGradient) {
            paint.shader = createGradient()
        }

        if (useShadow) {
            canvas.drawText(textStr, baseX, baseY, shadowPaint)
        }

        canvas.drawText(textStr, baseX, baseY, outlinePaint)

        super.onDraw(canvas)

        paint.shader = null
    }

    private fun createGradient(): LinearGradient {
        return when (gradientAngle) {
            0 -> LinearGradient(
                0f, 0f, width.toFloat(), 0f,
                gradientStartColor, gradientEndColor, Shader.TileMode.CLAMP
            )
            45 -> LinearGradient(
                0f, 0f, width.toFloat(), height.toFloat(),
                gradientStartColor, gradientEndColor, Shader.TileMode.CLAMP
            )
            90 -> LinearGradient(
                0f, 0f, 0f, height.toFloat(),
                gradientStartColor, gradientEndColor, Shader.TileMode.CLAMP
            )
            else -> LinearGradient(
                0f, 0f, 0f, height.toFloat(),
                gradientStartColor, gradientEndColor, Shader.TileMode.CLAMP
            )
        }
    }


}