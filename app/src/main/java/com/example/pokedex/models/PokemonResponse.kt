package com.example.pokedex.models

class PokemonResponse(val data: Data) {
    inner class Data(val list: List<UnParsedPokemon>) {
        inner class  UnParsedPokemon(val id: Int, val name: String, val details: List<Details>){
            inner class Details(val types: List<Types>) {
                inner class Types(val type: Type) {
                    inner class Type(val name: String)
                }
            }
        }
    }
}