package com.example.pokedex.models

class PokemonDetailResponse(val data: Data) {
    inner class Data(val details: List<Details>) {
        inner class Details(
            val cycle: Int,
            val gender: Int,
            val pokemon: List<PokemonDetails>,
            val egg: List<Egg>,
            val evolutions: Evolutions
        ) {
            inner class PokemonDetails(
                val height: Int,
                val weight: Int,
                val about: About,
                val abilities: List<Abilities>
            ) {
                inner class About(
                    val description: List<Description>,
                    val category: List<Category>
                ) {
                    inner class Description(val text: String)
                    inner class Category(val genus: String)
                }

                inner class Abilities(val ability: Ability) {
                    inner class Ability(val name: String)
                }
            }

            inner class Egg(val group: Group) {
                inner class Group(val name: String)
            }

            inner class Evolutions(val evolution: List<Evolution>) {
                inner class Evolution(val id: Int, val name: String)
            }
        }
    }
}