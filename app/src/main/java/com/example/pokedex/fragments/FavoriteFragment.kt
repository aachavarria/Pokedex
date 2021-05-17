package com.example.pokedex.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.pokedex.R
import com.example.pokedex.viewmodels.CreateFavoriteViewModel
import com.example.pokedex.viewmodels.FavoriteListViewModel

class FavoriteFragment : Fragment(R.layout.fragment_favorite) {
    private val favoriteViewModel: FavoriteListViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        favoriteViewModel.favoriteList(2)
    }
}
