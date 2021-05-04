package com.example.pokedex.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.pokedex.R
import com.example.pokedex.adapter.PokemonCardAdapter
import com.example.pokedex.databinding.FragmentPokedexBinding
import com.example.pokedex.viewmodels.PokemonListViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class PokedexFragment : Fragment(R.layout.fragment_pokedex) {
    private var _binding: FragmentPokedexBinding? = null
    private val binding: FragmentPokedexBinding get() = _binding!!
    private val adapter = PokemonCardAdapter()
    private val viewModel: PokemonListViewModel by viewModels()
    private val disposables: CompositeDisposable = CompositeDisposable()

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

        // TODO: moveremos esta logica al adapter y aqui nos vamos a subscribir, usando subjets
//        binding.detailsButton.setOnClickListener {
//            val action = PokedexFragmentDirections.actionPokedexFragmentDestToDetailsFragmentDest(1)
//            findNavController().navigate(action)
//        }

        viewModel.getPokemonList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { pokemonList ->
                adapter.pokemonList = pokemonList
//                pokemonrecyvlerviewlblabla.visibility = View.VISIBLE
            }
    }
}