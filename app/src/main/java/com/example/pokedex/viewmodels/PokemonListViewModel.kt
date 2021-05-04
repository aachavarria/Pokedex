package com.example.pokedex.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pokedex.api.APIService
import com.example.pokedex.api.ServiceBuilder
import com.example.pokedex.models.Pokemon
import com.example.pokedex.models.PokemonResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PokemonListViewModel: ViewModel() {

    private val pokemonList = MutableLiveData<List<Pokemon>>()
    private var service: APIService = ServiceBuilder.buildService(APIService::class.java)

    fun makeAPIRequest() {
        service.getPokemonList().enqueue(
            object : Callback<PokemonResponse> {
                override fun onFailure(call: Call<PokemonResponse>, t: Throwable) {
                }
                override fun onResponse( call: Call<PokemonResponse>, response: Response<PokemonResponse>) {
                }
            }
        )
    }

    fun getPokemonList(): LiveData<List<Pokemon>> {
        return pokemonList
    }

}
