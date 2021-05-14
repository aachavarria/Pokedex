package com.example.pokedex.fragments

import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.pokedex.R
import com.example.pokedex.adapter.ViewPagerDetailsAdapter
import com.example.pokedex.databinding.FragmentDetailsBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.util.*

class DetailsFragment : Fragment(R.layout.fragment_details) {
    val titles = listOf<String>("About", "Evolution")
    private lateinit var adapter: ViewPagerDetailsAdapter
    private var _binding: FragmentDetailsBinding? = null
    private val binding: FragmentDetailsBinding get() = _binding!!

    val args: DetailsFragmentArgs by navArgs()


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pokemon = args.pokemon

        Picasso.get().load(pokemon.imageUrl).fit().noFade().centerInside().into(binding.pokemonImage, object:
            Callback {
            override fun onSuccess() {
                binding.pokemonImage.alpha = 0f
                binding.pokemonImage.animate().setDuration(200).alpha(1f).start()
            }

            override fun onError(e: Exception?) {
            }
        })

        binding.pokemonName.text = pokemon.name.capitalize(Locale.ROOT)
        val pokemonNumberText = when (pokemon.id) {
            in 1..9 -> "#00" + pokemon.id.toString()
            in 10..99 -> "#0" + pokemon.id.toString()
            else -> { // Note the block
                '#' + pokemon.id.toString().capitalize(Locale.ROOT)
            }
        }

        binding.number.text = pokemonNumberText
        binding.pokemonType1.text = pokemon.types[0].capitalize(Locale.ROOT)

        val context: Context = binding.detailsLayout.context
        val cardColorID: Int = binding.detailsLayout.resources.getIdentifier("card_${pokemon.types[0]}", "color", view.context.packageName)
        val chipColorID: Int = binding.detailsLayout.resources.getIdentifier("chip_${pokemon.types[0]}", "color", view.context.packageName)
        binding.detailsLayout.setBackgroundColor(binding.detailsLayout.context.getColor(cardColorID))
        binding.pokemonType1.chipBackgroundColor = ColorStateList.valueOf(ContextCompat.getColor(context, chipColorID))
        if(pokemon.types.size > 1) {
            binding.pokemonType2.text = pokemon.types[1].capitalize(Locale.ROOT)
            binding.pokemonType2.visibility = View.VISIBLE
            val chip2ColorID: Int = binding.detailsLayout.resources.getIdentifier("chip_${pokemon.types[1]}", "color", context.packageName)
            binding.pokemonType2.chipBackgroundColor = ColorStateList.valueOf(ContextCompat.getColor(context, chip2ColorID))
        }


        adapter = ViewPagerDetailsAdapter(this)
        binding.viewPager.adapter = adapter
        TabLayoutMediator(binding.tabID, binding.viewPager) { tab, position ->
            tab.text = titles[position]
        }.attach()
    }
}