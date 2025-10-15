package com.example.qurio.utils

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.cardview.widget.CardView
import androidx.core.graphics.toColorInt
import com.example.qurio.R
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textview.MaterialTextView

class ReusableGameCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {

    private val categoryImage: ShapeableImageView
    private val gradientOverlay: ShapeableImageView
    private val categoryLabel: MaterialTextView

    init {
        LayoutInflater.from(context).inflate(R.layout.games_card, this, true)
        categoryImage = findViewById(R.id.category_image)
        gradientOverlay = findViewById(R.id.gradient_overlay)
        categoryLabel = findViewById(R.id.category_label)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ReusableGameCardView)

        try {
            val imageResId = typedArray.getResourceId(R.styleable.ReusableGameCardView_cardImage, 0)
            if (imageResId != 0) {
                categoryImage.setImageResource(imageResId)
            }

            val labelText = typedArray.getString(R.styleable.ReusableGameCardView_cardLabel)
            categoryLabel.text = labelText

            val strokeColor = typedArray.getColor(R.styleable.ReusableGameCardView_cardStrokeColor, Color.BLACK)
            categoryImage.strokeColor = ColorStateList.valueOf(strokeColor)

            val overlayColor = typedArray.getColor(R.styleable.ReusableGameCardView_cardOverlayColor, "#80000000".toColorInt())

            val colors = intArrayOf(Color.TRANSPARENT, overlayColor)
            val gradientDrawable = GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                colors
            )

            gradientOverlay.background = gradientDrawable

        } finally {
            typedArray.recycle()
        }
    }
}