package com.example.pokedex.adapter
import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.databinding.PokemonCardBinding
import com.example.pokedex.models.Pokemon
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject


class PokemonCardAdapter : PagingDataAdapter<Pokemon, PokemonCardAdapter.MyViewHolder>(
    PokemonComparator
) {
    class MyViewHolder(val binding: PokemonCardBinding) : RecyclerView.ViewHolder(binding.root)

    private val clicksAcceptor = PublishSubject.create<Pokemon>()

    val itemClicked: Observable<Pokemon> = clicksAcceptor.hide()

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
        holder.itemView.setOnClickListener {
            if (pokemon != null) {
                clicksAcceptor.onNext(pokemon)
            }
        }
        Picasso.get().load(pokemon?.imageUrl).fit().noFade().centerInside().into(holder.binding.imageView, object: Callback {
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
            val context: Context =  holder.binding.card.context
            val cardColorID: Int = holder.binding.card.resources.getIdentifier("card_${pokemon.types[0]}", "color", context.packageName)
            val chipColorID: Int = holder.binding.card.resources.getIdentifier("chip_${pokemon.types[0]}", "color", context.packageName)
            holder.binding.card.setCardBackgroundColor( holder.binding.card.context.getColor(cardColorID))
            holder.binding.type1.chipBackgroundColor = ColorStateList.valueOf(ContextCompat.getColor(context, chipColorID))
            if(pokemon.types.size > 1) {
                holder.binding.type2.text = pokemon.types[1].capitalize()
                holder.binding.type2.visibility = View.VISIBLE
                val chip2ColorID: Int = holder.binding.card.resources.getIdentifier("chip_${pokemon.types[1]}", "color", context.packageName)
                holder.binding.type2.chipBackgroundColor = ColorStateList.valueOf(ContextCompat.getColor(context, chip2ColorID))
            } else {
                holder.binding.type2.visibility = View.GONE
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