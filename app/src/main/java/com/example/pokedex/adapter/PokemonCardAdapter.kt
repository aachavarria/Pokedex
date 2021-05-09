package com.example.pokedex.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.R
import com.example.pokedex.databinding.PokemonCardBinding
import com.example.pokedex.models.Pokemon
import com.squareup.picasso.Picasso

class PokemonCardAdapter : PagingDataAdapter<Pokemon, PokemonCardAdapter.MyViewHolder>(
    PokemonComparator
) {

    class MyViewHolder(val binding: PokemonCardBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            PokemonCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            holder.itemView.findNavController().navigate(R.id.action_pokedexFragmentDest_to_detailsFragmentDest)
        }
        val pokemon = getItem(position)
        Picasso.get().load(pokemon?.imageUrl).fit().noFade().centerInside().into(holder.binding.imageView, object: com.squareup.picasso.Callback {
            override fun onSuccess() {
                holder.binding.imageView.alpha = 0f
                holder.binding.imageView.animate().setDuration(200).alpha(1f).start()
            }

            override fun onError(e: java.lang.Exception?) {
            }
        })
        if (pokemon != null) {
            holder.binding.textView.text = pokemon.name
            holder.binding.textView2.text = pokemon.id.toString()
            holder.binding.type1.text = pokemon.types[0]
            // TODO: no se como se hace jojo pero esto CardType.grass.color devuelve los colores
            // hay q agregar todos los colores de las cards
            // holder.binding.textView.textColor = CardType.grass.color
            if(pokemon.types.size > 1) {
                holder.binding.type2.text = pokemon.types[1]
                holder.binding.type2.visibility = View.VISIBLE
            }
        }
    }

    object PokemonComparator : DiffUtil.ItemCallback<Pokemon>() {
        override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
            return oldItem == newItem
        }
    }

}