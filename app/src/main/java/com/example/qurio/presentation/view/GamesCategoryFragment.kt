package com.example.qurio.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.qurio.R
import com.example.qurio.base.BaseFragment
import com.example.qurio.domain.entity.GameCategory
import com.example.qurio.presentation.adaptor.GameCategoryAdapter
// Assuming this is your generated binding class for fragment_games.xml
import com.example.qurio.databinding.FragmentGamesBinding

class GamesCategoryFragment : BaseFragment<FragmentGamesBinding, GamesCategoryView>() {

    private lateinit var adapter: GameCategoryAdapter

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentGamesBinding {
        return FragmentGamesBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.screenTitle.text = getString(R.string.games_title)

        binding.headerLayout.findViewById<View>(R.id.backButton)?.setOnClickListener {
            findNavController().popBackStack()
        }

        val gameCategories = listOf(
            GameCategory(12, getString(R.string.category_music), R.drawable.music, R.color.secondary, R.color.secondary),
            GameCategory(2, getString(R.string.category_food_drink), R.drawable.food_and_drinks, R.color.yellow, R.color.yellow),
            GameCategory(22, getString(R.string.category_geography), R.drawable.geography, R.color.green, R.color.green),
            GameCategory(9, getString(R.string.category_general_knowledge), R.drawable.general_knowledge, R.color.orange, R.color.orange),
            GameCategory(14, getString(R.string.category_film_tv), R.drawable.film_and_tv, R.color.secondary, R.color.secondary),
            GameCategory(24, getString(R.string.category_society_culture), R.drawable.society_and_culture, R.color.orange, R.color.orange),
            GameCategory(23, getString(R.string.category_history), R.drawable.history, R.color.orange, R.color.orange),
            GameCategory(17, getString(R.string.category_science), R.drawable.science, R.color.green, R.color.green),
            GameCategory(21, getString(R.string.category_sport_leisure), R.drawable.sport, R.color.primary, R.color.primary),
            GameCategory(25, getString(R.string.category_arts_literature), R.drawable.arts, R.color.primary, R.color.primary)
        )

        adapter = GameCategoryAdapter(gameCategories) { category ->
            val bundle = Bundle().apply {
                putInt("genreId", category.id)
            }
            findNavController().navigate(R.id.itemQuestionFragment, bundle)
        }

        binding.gamesRecyclerView.adapter = adapter
    }
}