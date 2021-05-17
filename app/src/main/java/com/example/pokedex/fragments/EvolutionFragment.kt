package com.example.pokedex.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.pokedex.R
import com.example.pokedex.adapter.EvolutionsAdapter
import com.example.pokedex.databinding.FragmentEvolutionBinding
import com.example.pokedex.models.PokemonDetail
import com.example.pokedex.rxbus.RxBus
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class EvolutionFragment : Fragment(R.layout.fragment_evolution){
    private var _binding: FragmentEvolutionBinding? = null
    private val binding: FragmentEvolutionBinding get() = _binding!!
    private val disposables = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEvolutionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        RxBus.instance?.listenLastOne()?.let {
            disposables.add(
                it.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { data ->
                    val pokemonDetails = Gson().fromJson(data, PokemonDetail::class.java)
                        if(pokemonDetails.evolutions.isEmpty()) {
                          binding.emptyStateView.visibility = View.VISIBLE
                        }
                    binding.gridView.adapter = EvolutionsAdapter(view.context, pokemonDetails.evolutions)
                })
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        disposables.clear()
        _binding = null
    }
}