package com.example.pokedex.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.paging.LoadState
import com.example.pokedex.R
import com.example.pokedex.adapter.PokemonCardAdapter
import com.example.pokedex.databinding.FragmentPokedexBinding
import com.example.pokedex.utils.Utils.hideKeyboard
import com.example.pokedex.viewmodels.FavoriteListViewModel
import com.example.pokedex.viewmodels.PokemonListViewModel
import com.example.pokedex.viewmodels.PokemonListViewModelType
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit


class PokedexFragment : Fragment(R.layout.fragment_pokedex) {
    private var _binding: FragmentPokedexBinding? = null
    private val binding: FragmentPokedexBinding get() = _binding!!
    private val adapter = PokemonCardAdapter()
    private val disposables = CompositeDisposable()

    private lateinit var viewModel: PokemonListViewModelType
    private val favoriteViewModel: FavoriteListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(PokemonListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPokedexBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        disposables.clear()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.pokemonCardRecyclerView.adapter = adapter

        adapter.addLoadStateListener { loadState ->
            if (loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && adapter.itemCount < 1) {
                binding.pokemonCardRecyclerView.isVisible = false
                binding.progressBar.isVisible = false
                binding.EmptyResultsView.isVisible = true
            } else if(loadState.source.refresh is LoadState.Loading) {
                binding.pokemonCardRecyclerView.isVisible = false
                binding.EmptyResultsView.isVisible = false
                binding.progressBar.isVisible = true
            } else {
                binding.EmptyResultsView.isVisible = false
                binding.progressBar.isVisible = false
                binding.pokemonCardRecyclerView.isVisible = true
            }
        }

        disposables.add(
            favoriteViewModel.favoriteList(1).subscribe{
                adapter.favoritesList.onNext(it)
            }
        )

        disposables.add(
            viewModel.outputs.listData
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    adapter.submitData(lifecycle, it)
                }
        )

        disposables.add(
            RxTextView.textChanges(binding.searchFieldTextInput)
                .debounce(300, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .map { it.toString() }
                .subscribe {
                    viewModel.inputs.keyword.onNext(it)
                }
        )

        disposables.add(
            binding.EmptyResultsView.itemClicked
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    binding.searchFieldTextInput.setText("")
                }
        )

        disposables.add(
            adapter.itemClicked
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    activity?.let { it1 -> hideKeyboard(it1) }
                    val action =
                        PokedexFragmentDirections.actionPokedexFragmentDestToDetailsFragmentDest(
                            it
                        )
                    view.findNavController().navigate(action)
                }
        )

        disposables.add(
            adapter.favoriteClicked
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if(it.isChecked == true) {
                        favoriteViewModel.createFavorite(it)
                    } else {
                        favoriteViewModel.removeFavorite(it.id)
                    }
                }
        )
    }
}