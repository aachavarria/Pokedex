package com.example.pokedex.datasource

import android.net.Uri
import androidx.paging.PagingState
import androidx.paging.rxjava2.RxPagingSource
import com.example.pokedex.api.APIService
import com.example.pokedex.models.Pokemon
import com.example.pokedex.utils.Constants.IMAGE_URL
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class PokemonDataSource(private val apiService: APIService) : RxPagingSource<String, Pokemon>() {
    override fun loadSingle(params: LoadParams<String>): Single<LoadResult<String, Pokemon>> {
        return apiService.getPokemonList()
            .subscribeOn(Schedulers.io())
            .map { result ->
                LoadResult.Page(
                    data = result.results.map { pokemon ->
                        val uri: Uri = Uri.parse(pokemon.url)
                        Pokemon(
                            uri.lastPathSegment,
                            pokemon.name,
                            pokemon.url,
                            "${IMAGE_URL}${uri.lastPathSegment}.png"
                        )
                    },
                    prevKey = result.previous,
                    nextKey = result.previous
                )
            }
    }

    override fun getRefreshKey(state: PagingState<String, Pokemon>): String? {
        return state.anchorPosition?.let { state.closestItemToPosition(it)?.id }
    }
}