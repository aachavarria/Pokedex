package com.example.pokedex.fragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.pokedex.R

class PokedexFragment : Fragment(R.layout.fragment_pokedex){
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.detailsButton)?.setOnClickListener {
            val action = PokedexFragmentDirections.actionPokedexFragmentDestToDetailsFragmentDest(1)
            findNavController().navigate(action)
        }
    }
}


