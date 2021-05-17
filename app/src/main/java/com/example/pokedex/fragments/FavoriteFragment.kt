package com.example.pokedex.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.pokedex.R
import com.example.pokedex.adapter.ViewFavoriteAdapter
import com.example.pokedex.databinding.FragmentAboutBinding
import com.example.pokedex.databinding.FragmentFavoriteBinding
import com.example.pokedex.databinding.FragmentPokedexBinding
import com.example.pokedex.databinding.PokemonCardBinding
import com.example.pokedex.db.entities.Favorite
import com.example.pokedex.models.Pokemon
import com.example.pokedex.viewmodels.CreateFavoriteViewModel
import com.example.pokedex.viewmodels.FavoriteListViewModel

class FavoriteFragment : Fragment(R.layout.fragment_favorite) {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding: FragmentFavoriteBinding get() = _binding!!
    private val favoriteViewModel: FavoriteListViewModel by viewModels()
    private val adapter = ViewFavoriteAdapter()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.pokemonCardRecyclerView.adapter = adapter
        favoriteViewModel.favoriteList(2).observe(viewLifecycleOwner) {
            binding.emptyState.visibility = View.GONE
            adapter.favorites = it.map { favorite -> Pokemon(favorite.pokemonId, favorite.name, favorite.imageUrl, favorite.types.split(",")) }
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
