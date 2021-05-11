package com.example.pokedex.api

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
//        @Query("offset") offset: Int? = null,
//        @Query("limit") limit: Int? = null
    ): Single<PokemonResponse>

//    @GET("pokemon/{id}")
//    fun getPokemonList(@Path("id") id: Int): Single<PokemonResponse>
}