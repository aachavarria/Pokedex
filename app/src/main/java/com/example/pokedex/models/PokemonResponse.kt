package com.example.pokedex.models

class PokemonResponse(val count: Int, var next: String, val previous: String, val results: List<Pokemon>)