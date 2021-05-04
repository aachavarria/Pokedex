package com.example.pokedex.api

import com.example.pokedex.models.PokemonResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface APIService {
    @GET("pokemon")
    fun getPokemonList(
        @Query("limit") limit: String? = null,
        @Query("offset") offset: String? = null
    ): Observable<PokemonResponse>

    @GET("pokemon/{id}")
    fun getPokemonList(@Path("id") id: Int): Observable<PokemonResponse>
}