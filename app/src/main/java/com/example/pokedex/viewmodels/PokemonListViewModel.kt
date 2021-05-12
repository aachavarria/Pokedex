package com.example.pokedex.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.cachedIn
import androidx.paging.rxjava2.observable
import com.example.pokedex.api.APIService
import com.example.pokedex.api.ServiceBuilder
import com.example.pokedex.datasource.PokemonDataSource
import com.example.pokedex.models.Pokemon
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

class PokemonListViewModel() : ViewModel() {

    private var service: APIService = ServiceBuilder.buildService(APIService::class.java)

    val keyword = BehaviorSubject.create<String>()

    val listData: Observable<PagingData<Pokemon>> = keyword.switchMap { keyword ->
        Pager(PagingConfig(pageSize = 10)) {
            PokemonDataSource(service, keyword)
        }.observable.cachedIn(viewModelScope)
    }


//    val listData = Pager(PagingConfig(pageSize = 10)) {
//        PokemonDataSource(service)
//    }.observable.cachedIn(viewModelScope)

}
