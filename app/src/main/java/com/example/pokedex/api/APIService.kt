package com.example.pokedex.api

import com.example.pokedex.models.PokemonResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface APIService {
    @GET("pokemon")
    fun getPokemonList(
        @Query("limit") limit: String? = null,
        @Query("offset") offset: String? = null
    ): Call<PokemonResponse>

    @GET("pokemon/{id}")
    fun getPokemonList(@Path("id") id: Int): Call<PokemonResponse>
}