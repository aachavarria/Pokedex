package com.example.pokedex.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.pokedex.R
import com.example.pokedex.adapter.ViewFavoriteAdapter
import com.example.pokedex.databinding.FragmentFavoriteBinding
import com.example.pokedex.databinding.FragmentPokedexBinding
import com.example.pokedex.models.Pokemon
import com.example.pokedex.utils.Utils
import com.example.pokedex.viewmodels.CurrentUserViewModel
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

    private lateinit var currentUserViewModel: CurrentUserViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)

        currentUserViewModel = ViewModelProvider(requireActivity()).get(CurrentUserViewModel::class.java)

        currentUserViewModel.selectedItem.observe(viewLifecycleOwner, { user ->

        disposables.add(
            adapter.favoriteClicked
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    favoriteViewModel.removeFavorite(it.id, user.id)
                }
        )
        disposables.add(
            favoriteViewModel.favoriteList(user.id)
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
        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.pokemonCardRecyclerView.adapter = adapter

        disposables.add(
            adapter.itemClicked
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    val action = FavoriteFragmentDirections.actionFavoriteFragmentDestToDetailsFragmentDest(it)
                    view.findNavController().navigate(action)
                }
        )
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        disposables.clear()
    }
}
