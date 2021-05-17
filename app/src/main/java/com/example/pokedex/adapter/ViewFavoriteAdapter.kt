package com.example.pokedex.adapter

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.pokedex.databinding.FragmentFavoriteBinding
import com.example.pokedex.databinding.PokemonCardBinding
import com.example.pokedex.db.entities.Favorite
import com.example.pokedex.db.entities.UserFavorites
import com.example.pokedex.fragments.AboutFragment
import com.example.pokedex.fragments.DetailsFragment
import com.example.pokedex.fragments.EvolutionFragment
import com.example.pokedex.fragments.FavoriteFragment
import com.example.pokedex.models.Pokemon
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class ViewFavoriteAdapter: RecyclerView.Adapter<ViewFavoriteAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: PokemonCardBinding) : RecyclerView.ViewHolder(binding.root)

    //TODO Paso 5, creamos el set para la lista que va a alimentar el adapter (datasource)
    var favorites: List<Pokemon> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewFavoriteAdapter.MyViewHolder {
        return ViewFavoriteAdapter.MyViewHolder(
            PokemonCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val pokemon = favorites[position]

        Picasso.get().load(pokemon?.imageUrl).fit().noFade().centerInside()
            .into(holder.binding.imageView, object :
                Callback {
                override fun onSuccess() {
                    holder.binding.imageView.alpha = 0f
                    holder.binding.imageView.animate().setDuration(200).alpha(1f).start()
                }

                override fun onError(e: Exception?) {
                }
            })
        if (pokemon != null) {
            holder.binding.textView.text = pokemon.name.capitalize()
            val pokemonNumberText = when (pokemon.id) {
                in 1..9 -> "#00" + pokemon.id.toString()
                in 10..99 -> "#0" + pokemon.id.toString()
                else -> { // Note the block
                    '#' + pokemon.id.toString().capitalize()
                }
            }

            holder.binding.textView2.text = pokemonNumberText
            holder.binding.type1.text = pokemon.types[0].capitalize()
            holder.binding.favoriteIcon.isChecked = true
            val context: Context = holder.binding.card.context
            val cardColorID: Int = holder.binding.card.resources.getIdentifier(
                "card_${pokemon.types[0]}",
                "color",
                context.packageName
            )
            val chipColorID: Int = holder.binding.card.resources.getIdentifier(
                "chip_${pokemon.types[0]}",
                "color",
                context.packageName
            )
            holder.binding.card.setCardBackgroundColor(
                holder.binding.card.context.getColor(
                    cardColorID
                )
            )
            holder.binding.type1.chipBackgroundColor =
                ColorStateList.valueOf(ContextCompat.getColor(context, chipColorID))
            if (pokemon.types.size > 1) {
                holder.binding.type2.text = pokemon.types[1].capitalize()
                holder.binding.type2.visibility = View.VISIBLE
                val chip2ColorID: Int = holder.binding.card.resources.getIdentifier(
                    "chip_${pokemon.types[1]}",
                    "color",
                    context.packageName
                )
                holder.binding.type2.chipBackgroundColor =
                    ColorStateList.valueOf(ContextCompat.getColor(context, chip2ColorID))
            } else {
                holder.binding.type2.visibility = View.GONE
            }
        }
    }

    override fun getItemCount(): Int {
        return favorites.size
    }
}


