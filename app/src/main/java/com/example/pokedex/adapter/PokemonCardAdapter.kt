package com.example.pokedex.adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
//import com.bumptech.glide.Glide
import com.example.pokedex.R
import com.example.pokedex.models.Pokemon

class PokemonCardAdapter: RecyclerView.Adapter<PokemonCardAdapter.PokemonCardViewHolder>()  {
    //TODO Paso 5, creamos el set para la lista que va a alimentar el adapter (datasource)
    var pokemons: List<Pokemon> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    //    TODO Paso 3, Creamos el ViewHolder
    inner class PokemonCardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(pokemon: Pokemon) {
            itemView.textView2.text = pokemon.name

            // TODO Click listener de la celda
//            itemView.setOnClickListener {
//                itemView.textView2.visibility = itemView.textView2.isVisible.not().mapToVisibility()
//            }

//            Picasso.get().load(imageUrl).into(itemView.imageView)
//            Glide.with(itemView.context)
//                    .load(imageUrl)
//                    .circleCrop()
//                    .into(itemView.imageView)
//
        }
    }

    //TODO Metodo encargado de inflar el layout/xml en cada celda
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonCardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.pokemon_card, parent, false)
        return PokemonCardViewHolder(view)
    }

    // TODO Metodo encargado de pintar los datos para cada celda, dependiendo de la posicion
    override fun onBindViewHolder(holder: PokemonCardViewHolder, position: Int) {
        // Llamamos al metodo bind para la posicion especifica
        val pokemon = pokemons[position]
        holder.bind(pokemon)
    }

    // TODO Metodo que determina la cantidad de elementos en la lista
    override fun getItemCount(): Int = pokemons.size

}