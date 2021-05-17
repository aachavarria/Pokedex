package com.example.pokedex.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.pokedex.R
import com.example.pokedex.databinding.FragmentAboutBinding
import com.example.pokedex.models.PokemonDetail
import com.example.pokedex.rxbus.RxBus
import com.google.gson.Gson
import io.reactivex.disposables.CompositeDisposable


class AboutFragment : Fragment(R.layout.fragment_about) {
    private val disposables = CompositeDisposable()
    private var _binding: FragmentAboutBinding? = null
    private val binding: FragmentAboutBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAboutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        RxBus.instance?.listen()?.let {
            disposables.add(
                it.subscribe { data ->
                    binding.progressBar.visibility = View.GONE
                    val pokemonDetails = Gson().fromJson(data, PokemonDetail::class.java)
                    binding.description.text = pokemonDetails.description
                    binding.ability.text = pokemonDetails.abilities[0]
                    binding.weight.text = pokemonDetails.weight.toString()
                    binding.height.text = "${(pokemonDetails.height / 10.0f)} m"
                    binding.category.text = pokemonDetails.category.toString()
                })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposables.clear()
        _binding = null
    }
}