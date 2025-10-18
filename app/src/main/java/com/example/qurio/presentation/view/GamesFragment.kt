package com.example.qurio.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.qurio.R
import com.example.qurio.domain.entity.GameCategory
import com.example.qurio.presentation.adaptor.GameCategoryAdapter

class GamesFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: GameCategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_games, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.games_recycler_view)

        val gameCategories = listOf(
            GameCategory(1, "Music", R.drawable.music, R.color.secondary, R.color.secondary),
            GameCategory(2, "Food & Drink", R.drawable.food_and_drinks, R.color.yellow, R.color.yellow),
            GameCategory(3, "Geography", R.drawable.geography, R.color.green, R.color.green),
            GameCategory(4, "General knowledge", R.drawable.general_knowledge, R.color.orange, R.color.orange),
            GameCategory(5, "Film & TV", R.drawable.film_and_tv, R.color.secondary, R.color.secondary),
            GameCategory(6,"Society & Culture", R.drawable.society_and_culture, R.color.orange, R.color.orange),
            GameCategory(7, "History", R.drawable.history, R.color.orange, R.color.orange),
            GameCategory(8, "Science", R.drawable.science, R.color.green, R.color.green),
            GameCategory(9, "Sport & Leisure", R.drawable.sport, R.color.primary, R.color.primary),
            GameCategory(10, "Arts & Literature", R.drawable.arts, R.color.primary, R.color.primary)
        )

        adapter = GameCategoryAdapter(gameCategories) { category ->
        }

        recyclerView.adapter = adapter
    }
}


