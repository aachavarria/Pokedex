package com.example.pokedex.fragments

import android.annotation.SuppressLint
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

class PokedexFragment : Fragment(R.layout.fragment_pokedex) {
    private var _binding: FragmentPokedexBinding? = null
    private val binding: FragmentPokedexBinding get() = _binding!!
    private val adapter = PokemonCardAdapter()
    private val viewModel: PokemonListViewModel by viewModels()

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
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.pokemonCardRecyclerView.adapter = adapter
        loadData()
    }

    @SuppressLint("CheckResult")
    private fun loadData() {
        viewModel.listData.subscribe {
            adapter.submitData(lifecycle,it)
        }
    }
}