package com.example.pokedex.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.pokedex.R
import com.example.pokedex.adapter.ViewFavoriteAdapter
import com.example.pokedex.databinding.FragmentFavoriteBinding
import com.example.pokedex.models.Pokemon
import com.example.pokedex.viewmodels.FavoriteListViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class FavoriteFragment : Fragment(R.layout.fragment_favorite) {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding: FragmentFavoriteBinding get() = _binding!!
    private val favoriteViewModel: FavoriteListViewModel by viewModels()
    private val adapter = ViewFavoriteAdapter()
    private val disposables = CompositeDisposable()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.pokemonCardRecyclerView.adapter = adapter
        disposables.add(
            favoriteViewModel.favoriteList(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if(it.isEmpty()) {
                        binding.emptyState.visibility = View.VISIBLE
                        binding.pokemonCardRecyclerView.visibility = View.GONE
                    } else {
                        binding.emptyState.visibility = View.GONE
                        binding.pokemonCardRecyclerView.visibility = View.VISIBLE
                    }
                    adapter.favorites = it.map { favorite -> Pokemon(favorite.pokemonId, favorite.name, favorite.imageUrl, favorite.types.split(",")) }
                }
        )

        disposables.add(
            adapter.favoriteClicked
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    favoriteViewModel.removeFavorite(it.id)
                }
        )
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
        disposables.clear()
    }
}
