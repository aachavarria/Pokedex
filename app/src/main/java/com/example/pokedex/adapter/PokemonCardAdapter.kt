package com.example.pokedex.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.databinding.PokemonCardBinding
import com.example.pokedex.models.Pokemon
import com.squareup.picasso.Picasso

class PokemonCardAdapter : RecyclerView.Adapter<PokemonCardAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: PokemonCardBinding) : RecyclerView.ViewHolder(binding.root)

    var pokemonList: List<Pokemon> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(PokemonCardBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val pokemon = pokemonList[position]
        Picasso.get().load("https://pokeres.bastionbot.org/images/pokemon/${pokemon.id}.png").into(holder.binding.imageView);
        holder.binding.textView.text = pokemon.name
    }

    override fun getItemCount(): Int = pokemonList.size

}