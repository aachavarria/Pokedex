package com.example.pokedex.api

import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {

    private const val baseUrl = "https://pokeapi.co/api/v2/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()


    fun<T> buildService(service: Class<T>): T{
        return retrofit.create(service)
    }
}