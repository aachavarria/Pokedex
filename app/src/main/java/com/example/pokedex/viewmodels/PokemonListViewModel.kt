package com.example.pokedex.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
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
import io.reactivex.Observer
import io.reactivex.subjects.BehaviorSubject

interface PokemonListViewModelInputs {
    val keyword: Observer<String>
}

interface PokemonListViewModelOutputs {
    val listData: Observable<PagingData<Pokemon>>
}

interface PokemonListViewModelType {
    val inputs: PokemonListViewModelInputs
    val outputs: PokemonListViewModelOutputs
}

class PokemonListViewModel(
    application: Application
) : AndroidViewModel(application), PokemonListViewModelInputs, PokemonListViewModelOutputs, PokemonListViewModelType {

    private var service: APIService = ServiceBuilder.buildService(APIService::class.java)

    override val inputs: PokemonListViewModelInputs = this
    override val outputs: PokemonListViewModelOutputs = this

    override val keyword = BehaviorSubject.create<String>()
    override val listData: Observable<PagingData<Pokemon>> = keyword.switchMap { keyword ->
        Pager(PagingConfig(pageSize = 10)) {
            PokemonDataSource(service, keyword)
        }.observable.cachedIn(viewModelScope)
    }
}
