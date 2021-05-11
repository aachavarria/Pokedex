package com.example.pokedex.datasource

import android.util.Log
import androidx.paging.PagingState
import androidx.paging.rxjava2.RxPagingSource
import com.example.pokedex.api.APIService
import com.example.pokedex.models.Pokemon
import com.example.pokedex.utils.Constants.IMAGE_URL
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject



class PokemonDataSource(private val apiService: APIService) : RxPagingSource<Int, Pokemon>() {
    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, Pokemon>> {
        val page = params.key ?: 0
        val limit = 20
        val offset = page * limit
        val prevKey = if (page == 0) null else page - 0
        Log.e("offset", offset.toString())
        Log.e("limit", limit.toString())
        val paramObject = JSONObject()
        paramObject.put("query", "{species:pokemon_v2_pokemonspecies_aggregate{list:aggregate{count}}list:pokemon_v2_pokemonspecies(offset:${offset},limit:${limit},order_by:{id:asc},where:{name: {_ilike:\"%%\"}}){id name details:pokemon_v2_pokemons(distinct_on:pokemon_species_id){types:pokemon_v2_pokemontypes{type:pokemon_v2_type {name}}}}}")

        return apiService.getPokemonList(paramObject.toString())
            .subscribeOn(Schedulers.io())
            .map { result ->
                LoadResult.Page(
                    data = result.data.list.map { pokemon ->
                        Pokemon(
                            pokemon.id,
                            pokemon.name,
                            "${IMAGE_URL}${pokemon.id}.png",
                            pokemon.details[0].types.map { value -> value.type.name }
                        )
                    },
                    prevKey,
                    nextKey = if((result.data.species.list.count / limit) > page) page.plus(1) else null
                )
            }
    }

    override fun getRefreshKey(state: PagingState<Int, Pokemon>): Int? {
        return null
    }
}