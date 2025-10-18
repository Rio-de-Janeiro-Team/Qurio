package com.example.qurio.presentation.adaptor

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.qurio.domain.entity.GameCategory
import com.example.qurio.utils.ReusableGameCardView

class GameCategoryAdapter(
    private val categories: List<GameCategory>,
    private val clickListener: (GameCategory) -> Unit
) : RecyclerView.Adapter<GameCategoryAdapter.ViewHolder>() {

    inner class ViewHolder(private val cardView: ReusableGameCardView) :
        RecyclerView.ViewHolder(cardView) {

        fun bind(category: GameCategory) {

            cardView.setCardLabel(category.name)
            cardView.setCardImage(category.imageId)
            cardView.setColorsByResId(category.strokeColorResId, category.overlayColorResId)

            cardView.setOnClickListener {
                clickListener(category)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ReusableGameCardView(parent.context)

        val widthInPx = (180 * parent.context.resources.displayMetrics.density).toInt()
        val desiredHeightDp = 290
        val heightInPx = (desiredHeightDp * parent.context.resources.displayMetrics.density).toInt()

        val layoutParams = RecyclerView.LayoutParams(
            widthInPx,
            heightInPx
        )
        layoutParams.bottomMargin = (6 * parent.context.resources.displayMetrics.density).toInt()

        view.layoutParams = layoutParams

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(categories[position])
    }

    override fun getItemCount() = categories.size
}