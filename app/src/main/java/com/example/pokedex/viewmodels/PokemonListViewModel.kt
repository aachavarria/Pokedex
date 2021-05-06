package com.example.pokedex.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.rxjava2.cachedIn
import androidx.paging.rxjava2.observable
import com.example.pokedex.api.APIService
import com.example.pokedex.api.ServiceBuilder
import com.example.pokedex.datasource.PokemonDataSource

class PokemonListViewModel() : ViewModel() {

    private var service: APIService = ServiceBuilder.buildService(APIService::class.java)

    val listData = Pager(PagingConfig(pageSize = 20)) {
        PokemonDataSource(service)
    }.observable.cachedIn(viewModelScope)

}
