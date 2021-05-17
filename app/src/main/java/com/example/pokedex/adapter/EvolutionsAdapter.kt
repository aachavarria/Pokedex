package com.example.pokedex.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.pokedex.R
import com.example.pokedex.models.Pokemon
import com.squareup.picasso.Picasso


class EvolutionsAdapter(@get:JvmName("getContext_") private val context: Context, private val evolutions:
List<Pokemon>) :
    ArrayAdapter<Pokemon>(context, R.layout.row_items, evolutions) {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        if (null == convertView) {
            convertView = inflater.inflate(R.layout.row_items, parent, false)
        }
        if (convertView != null) {
            Picasso
                .get()
                .load(evolutions[position].imageUrl)
                .placeholder(R.drawable.placeholder)
                .fit()
                .into(convertView.findViewById(R.id.pokemonImage) as ImageView?)
            convertView.findViewById<TextView>(R.id.pokemonName).text = evolutions[position].name.capitalize()
        }
        return convertView!!
    }

}