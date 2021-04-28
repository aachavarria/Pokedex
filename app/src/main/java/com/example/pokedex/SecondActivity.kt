package com.example.pokedex

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.pokedex.databinding.SecondActivityBinding

class SecondActivity : AppCompatActivity() {
    private lateinit var binding: SecondActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SecondActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val navController = findNavController(R.id.fragment)
        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.page1 -> {
                    navController.navigateUp()
                    navController.navigate(R.id.trainerFragment)
                }
                R.id.page2 -> {
                    navController.navigateUp()
                    navController.navigate(R.id.action_trainerFragment_to_favoriteFragment)
                }
                R.id.page3 -> {
                    navController.navigateUp()
                    navController.navigate(R.id.action_trainerFragment_to_pokedexFragment)
                }
            }
            true
        }
    }
}