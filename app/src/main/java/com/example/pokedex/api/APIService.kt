package com.example.pokedex.api

import com.example.pokedex.models.PokemonResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface APIService {
    @GET("pokemon")
    fun getPokemonList(
        @Query("limit") limit: Int? = null,
        @Query("offset") offset: Int? = null
    ): Single<PokemonResponse>

    @GET("pokemon/{id}")
    fun getPokemonList(@Path("id") id: Int): Single<PokemonResponse>
}