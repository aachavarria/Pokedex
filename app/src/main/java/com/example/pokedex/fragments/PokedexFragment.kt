package com.example.pokedex.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.pokedex.R
import com.example.pokedex.databinding.FragmentPokedexBinding
import com.example.pokedex.adapter.PokemonCardAdapter
import com.example.pokedex.models.Pokemon

class PokedexFragment : Fragment(R.layout.fragment_pokedex) {
    private var _binding: FragmentPokedexBinding? = null
    private val binding: FragmentPokedexBinding get() = _binding!!
    private val adapter = PokemonCardAdapter()

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

        binding.detailsButton.setOnClickListener {
            val action = PokedexFragmentDirections.actionPokedexFragmentDestToDetailsFragmentDest(1)
            findNavController().navigate(action)
        }

        //TODO Recuerden agregar ya sea BindView o findViewById
        binding.pokemonCardRecyclerView.adapter = adapter
        //binding.pokemonCardRecyclerView.addItemDecoration(DividerItemDecoration(this, VERTICAL))

        adapter.pokemons = getDummyContacts()
    }

    private fun getDummyContacts() : List<Pokemon> {
        return mutableListOf(
            Pokemon(5,"holita",null, "https://pokeres.bastionbot.org/images/pokemon/5.png"),
            Pokemon(6,"holita",null, "https://pokeres.bastionbot.org/images/pokemon/6.png"),
            Pokemon(7,"holita",null, "https://pokeres.bastionbot.org/images/pokemon/7.png"),
        )
    }
}