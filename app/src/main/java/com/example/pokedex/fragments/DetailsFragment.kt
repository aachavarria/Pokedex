package com.example.pokedex.fragments

import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.pokedex.R
import com.example.pokedex.adapter.PokemonCardAdapter
import com.example.pokedex.adapter.ViewPagerDetailsAdapter
import com.example.pokedex.api.APIService
import com.example.pokedex.api.ServiceBuilder
import com.example.pokedex.databinding.FragmentDetailsBinding
import com.example.pokedex.models.Pokemon
import com.example.pokedex.models.PokemonDetail
import com.example.pokedex.rxbus.RxBus
import com.example.pokedex.utils.Constants
import com.example.pokedex.viewmodels.CreateFavoriteViewModel
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import java.util.*


class DetailsFragment : Fragment(R.layout.fragment_details) {
    private val titles = listOf<String>("About", "Evolution")
    private lateinit var adapter: ViewPagerDetailsAdapter
    private var _binding: FragmentDetailsBinding? = null
    private val binding: FragmentDetailsBinding get() = _binding!!
    private val disposables = CompositeDisposable()
    private var service: APIService = ServiceBuilder.buildService(APIService::class.java)
    private val favoriteViewModel: CreateFavoriteViewModel by viewModels()

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
        disposables.clear()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pokemon = args.pokemon

        Picasso.get().load(pokemon.imageUrl).fit().noFade().centerInside().into(
            binding.pokemonImage,
            object :
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
        val cardColorID: Int = binding.detailsLayout.resources.getIdentifier(
            "card_${pokemon.types[0]}",
            "color",
            view.context.packageName
        )
        val chipColorID: Int = binding.detailsLayout.resources.getIdentifier(
            "chip_${pokemon.types[0]}",
            "color",
            view.context.packageName
        )
        binding.detailsLayout.setBackgroundColor(binding.detailsLayout.context.getColor(cardColorID))
        binding.pokemonType1.chipBackgroundColor = ColorStateList.valueOf(
            ContextCompat.getColor(
                context,
                chipColorID
            )
        )
        if (pokemon.types.size > 1) {
            binding.pokemonType2.text = pokemon.types[1].capitalize(Locale.ROOT)
            binding.pokemonType2.visibility = View.VISIBLE
            val chip2ColorID: Int = binding.detailsLayout.resources.getIdentifier(
                "chip_${pokemon.types[1]}",
                "color",
                context.packageName
            )
            binding.pokemonType2.chipBackgroundColor = ColorStateList.valueOf(
                ContextCompat.getColor(
                    context,
                    chip2ColorID
                )
            )
        }

        binding.backButton.setOnClickListener {
            activity?.onBackPressed();
        }


        adapter = ViewPagerDetailsAdapter(this)
        binding.viewPager.adapter = adapter
        TabLayoutMediator(binding.tabID, binding.viewPager) { tab, position ->
            tab.text = titles[position]
        }.attach()

        val paramObject = JSONObject()
        paramObject.put(
            "query",
            "{details:pokemon_v2_pokemonspecies(where:{id:{_eq: ${pokemon.id}}}){cycle:hatch_counter gender:gender_rate pokemon:pokemon_v2_pokemons{height weight about:pokemon_v2_pokemonspecy{description:pokemon_v2_pokemonspeciesflavortexts(where:{language_id:{_eq:9}},distinct_on:language_id){text:flavor_text}category:pokemon_v2_pokemonspeciesnames(where:{language_id:{_eq:9}}){genus}}abilities:pokemon_v2_pokemonabilities(order_by:{id: asc}){ability:pokemon_v2_ability{name}}}egg:pokemon_v2_pokemonegggroups{group:pokemon_v2_egggroup{name}}evolutions:pokemon_v2_evolutionchain{evolution:pokemon_v2_pokemonspecies(order_by:{id:asc}where: {id: {_gt: ${pokemon.id}}}){id name}}}}"
        )

        disposables.add(
            service.getPokemonDetails(paramObject.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    val pokemonDetail: PokemonDetail = PokemonDetail(
                        result.data.details[0].pokemon[0].height,
                        result.data.details[0].pokemon[0].weight,
                        result.data.details[0].pokemon[0].about.description[0].text.replace(
                            "\n",
                            " "
                        ),
                        result.data.details[0].pokemon[0].about.category[0].genus.replace("Pokémon", ""),
                        result.data.details[0].pokemon[0].abilities.map { abilities ->
                            abilities.ability.name.capitalize(
                                Locale.ROOT
                            )
                        },
                        result.data.details[0].cycle,
                        result.data.details[0].gender,
                        result.data.details[0].egg.map { egg -> egg.group.name.capitalize(Locale.ROOT) },
                        result.data.details[0].evolutions.evolution.map { evolution -> Pokemon(evolution.id, evolution.name, "${Constants.IMAGE_URL}${evolution.id}.png", emptyList()) }
                    )
                    RxBus.instance?.publish(Gson().toJson(pokemonDetail));
                }, { throwable ->
                    throwable.printStackTrace()
                })
        )
        binding.favoriteCheckbox.setOnCheckedChangeListener { buttonView, isChecked ->
            favoriteViewModel.createFavorite(pokemon)
        }
    }
}