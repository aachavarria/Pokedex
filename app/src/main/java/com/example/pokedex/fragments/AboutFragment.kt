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
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


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
                it.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { data ->
                        binding.progressBar.visibility = View.GONE
                        val pokemonDetails = Gson().fromJson(data, PokemonDetail::class.java)
                        binding.description.text = pokemonDetails.description
                        binding.ability.text = pokemonDetails.abilities[0]
                        binding.weight.text = "${(pokemonDetails.height / 10.0f)} kg"
                        binding.height.text = "${(pokemonDetails.height / 10.0f)} m"
                        binding.category.text = pokemonDetails.category

                        when (pokemonDetails.genderRate) {
                            -1 -> {
                                binding.genderTextView1.text = "None (unknown gender)"
                            }
                            0 -> {
                                binding.genderTextView1.text = "100% male"
                            }
                            1 -> {
                                binding.genderTextView1.text = "87.5% male,"
                                binding.genderTextView2.text = "12.5% female"
                                binding.genderTextView2.visibility = View.VISIBLE
                            }
                            2 -> {
                                binding.genderTextView1.text = "75% male,"
                                binding.genderTextView2.text = "25% female"
                                binding.genderTextView2.visibility = View.VISIBLE
                            }
                            4 -> {
                                binding.genderTextView1.text = "50% male,"
                                binding.genderTextView2.text = "50% female"
                                binding.genderTextView2.visibility = View.VISIBLE
                            }
                            6 -> {
                                binding.genderTextView1.text = "25% male,"
                                binding.genderTextView2.text = "75% female"
                                binding.genderTextView2.visibility = View.VISIBLE
                            }
                            7 -> {
                                binding.genderTextView1.text = "12.5% male,"
                                binding.genderTextView2.text = "87.5% female"
                                binding.genderTextView2.visibility = View.VISIBLE
                            }
                            8 -> {
                                binding.genderTextView1.text = "100% female"
                                binding.genderTextView1.setTextColor(resources.getColor(R.color.orange_500))
                            }
                            else -> {
                                binding.genderTextView1.text = "None (unknown gender)"
                            }
                        }

                        binding.eggCycleTextView.text = pokemonDetails.eggCycle.toString()
                        binding.eggGroupsTextView.text = pokemonDetails.eggGroups.joinToString()
                    })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposables.clear()
        _binding = null
    }
}