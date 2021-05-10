package com.example.pokedex.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.pokedex.fragments.AboutFragment
import com.example.pokedex.fragments.DetailsFragment
import com.example.pokedex.fragments.EvolutionFragment

class ViewPagerDetailsAdapter(fragment: DetailsFragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0-> AboutFragment()
            1-> EvolutionFragment()
            else -> AboutFragment()
        }
    }
}