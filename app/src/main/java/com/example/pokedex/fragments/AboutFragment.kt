package com.example.pokedex.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.pokedex.R
import com.example.pokedex.models.PokemonDetail
import com.example.pokedex.rxbus.RxBus
import com.google.gson.Gson
import io.reactivex.disposables.CompositeDisposable


class AboutFragment : Fragment(R.layout.fragment_about) {
    private val disposables = CompositeDisposable()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        RxBus.instance?.listen()?.let {
            disposables.add(
                it.subscribe { data ->
                    val pokemonDetails = Gson().fromJson(data, PokemonDetail::class.java)
                })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposables.clear()
    }
}