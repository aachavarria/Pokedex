package com.example.pokedex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.fragment.app.Fragment
import com.example.pokedex.fragments.PokedexFragment
import com.example.pokedex.fragments.FavoriteFragment
import com.example.pokedex.fragments.TrainerFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var bottom_navigation_view = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        bottom_navigation_view.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_pokedex -> {
                    val fragment = PokedexFragment.newInstance()
                    openFragment(fragment)
                    true
                }
                R.id.navigation_favorites -> {
                    val fragment = FavoriteFragment.newInstance()
                    openFragment(fragment)
                    true
                }
                R.id.navigation_trainer -> {
                    val fragment = TrainerFragment.newInstance()
                    openFragment(fragment)
                    true
                }
                else -> false
            }
        }
        bottom_navigation_view.selectedItemId = R.id.navigation_pokedex
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}