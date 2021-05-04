package com.example.pokedex.viewmodels

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pokedex.api.APIService
import com.example.pokedex.api.ServiceBuilder
import com.example.pokedex.models.Pokemon
import com.example.pokedex.models.PokemonResponse
import io.reactivex.rxjava3.core.Observable

class PokemonListViewModel : ViewModel() {
    private val imageURL = "https://pokeres.bastionbot.org/images/pokemon/"

    private var service: APIService = ServiceBuilder.buildService(APIService::class.java)

    private val isMakingRequest: MutableLiveData<Boolean> = MutableLiveData()
    private val isError: MutableLiveData<Boolean> = MutableLiveData()


    fun getIsLoading(): LiveData<Boolean> {
        return isMakingRequest
    }

    fun getIsError(): LiveData<Boolean> {
        return isError
    }

    fun getPokemonList(): Observable<List<Pokemon>> {
        isMakingRequest.postValue(true)
        return service.getPokemonList()
            .map { response: PokemonResponse ->
                response.results.map { pokemon ->
                    val uri: Uri = Uri.parse(pokemon.url)
                    Pokemon(
                        uri.lastPathSegment?.toInt(),
                        pokemon.name,
                        pokemon.url,
                        "${imageURL}${uri.lastPathSegment}.png"
                    )
                }
            }
//            .doOnError { isError.postValue(true) }
//            .onErrorReturn { emptyList() }
//            .flatMapIterable { list -> list }
//            .flatMap { item ->
//                service.getPokemonList(id: blablabla)
//                    .map { detailResponse -> detailResponse.data.results[0] }
//            }
//            .toList()
//            .toObservable()
//            .doOnNext { isMakingRequest.postValue(false) }
    }
}
