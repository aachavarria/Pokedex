package com.example.pokedex.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.paging.LoadState
import com.example.pokedex.R
import com.example.pokedex.adapter.PokemonCardAdapter
import com.example.pokedex.databinding.FragmentPokedexBinding
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
        disposables.clear()
        _binding = null
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
    }
}