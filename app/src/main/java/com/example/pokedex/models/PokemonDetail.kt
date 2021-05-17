package com.example.pokedex.models

class PokemonDetail(
    val height: Int,
    val weight:Int, val description: String, val category: String, val abilities: List<String>, val eggCycle: Int, val genderRate: Int, val eggGroups: List<String>, val evolutions: List<Pokemon>
)