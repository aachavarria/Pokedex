package com.example.pokedex.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
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
        val pokemon = getItem(position)
        Picasso.get().load(pokemon?.imageUrl).fit().noFade().centerInside().into(holder.binding.imageView, object: com.squareup.picasso.Callback {
            override fun onSuccess() {
                holder.binding.imageView.alpha = 0f
                holder.binding.imageView.animate().setDuration(200).alpha(1f).start()
            }

            override fun onError(e: java.lang.Exception?) {
            }
        })
        holder.binding.textView.text = pokemon?.name
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