package com.example.pokedex.datasource

import android.net.Uri
import androidx.paging.PagingState
import androidx.paging.rxjava2.RxPagingSource
import com.example.pokedex.api.APIService
import com.example.pokedex.models.Pokemon
import com.example.pokedex.utils.Constants.IMAGE_URL
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class PokemonDataSource(private val apiService: APIService) : RxPagingSource<Int, Pokemon>() {
    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, Pokemon>> {
        val page = params.key ?: 0
        val limit = 20
        val offset = page * limit
        val prevKey = if (page == 0) null else page - 0
        return apiService.getPokemonList(offset, limit)
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
                    prevKey,
                    nextKey = page.plus(1)
                )
            }
    }

    override fun getRefreshKey(state: PagingState<Int, Pokemon>): Int? {
        return state.anchorPosition?.let { state.closestItemToPosition(it)?.id?.toInt() }
    }
}