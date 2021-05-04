package com.example.pokedex.models

class PokemonResponse(val count: Int, var next: String, val previous: String, results: List<Pokemon>)