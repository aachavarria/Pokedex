package com.example.pokedex.api

import com.example.pokedex.models.PokemonDetailResponse
import com.example.pokedex.models.PokemonResponse
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST


interface APIService {
    @Headers("Content-Type: application/json")
    @POST(" ")
    fun getPokemonList(
        @Body body: String
    ) : Single<PokemonResponse>

    @Headers("Content-Type: application/json")
    @POST(" ")
    fun getPokemonDetails(
        @Body body: String
    ) : Single<PokemonDetailResponse>
}