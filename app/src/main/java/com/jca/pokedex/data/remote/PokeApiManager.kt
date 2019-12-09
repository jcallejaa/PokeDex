package com.jca.pokedex.data.remote

import androidx.annotation.VisibleForTesting
import com.jca.pokedex.data.PokeRepository
import com.jca.pokedex.data.mappers.PokemonDataMapper
import com.jca.pokedex.domain.models.PokemonData
import com.jca.pokedex.domain.models.PokemonListElement
import com.jca.pokedex.domain.models.PokemonsList
import me.sargunvohra.lib.pokekotlin.client.PokeApiClient

class PokeApiManager : PokeRepository {

    companion object {
        const val TAG = "PokeApiManager"

        @VisibleForTesting
        val instance = PokeApiManager()

    }

    private var pokeApiClient = PokeApiClient()

    override fun getPokemonData(pokemonId: Int): PokemonData? {
        return try {
            val pokemonApiData = pokeApiClient.getPokemon(pokemonId)
            PokemonDataMapper.getInstance().map(pokemonApiData)!!
        } catch (e: Exception) {
            //Log.e(TAG, "Error trying to get pokemon $pokemonId")
            null
        }

    }

    fun setPokeApiClient(client: PokeApiClient) {
        pokeApiClient = client
    }

    override fun getPokemonsList(): PokemonsList {

        return getPokemonsList(0, 1000)

    }

    override fun getPokemonsList(start: Int, end: Int): PokemonsList {
        val pokemonApiDataList = pokeApiClient.getPokemonList(start, end)

        var resultArray = ArrayList<PokemonListElement>()

        if (pokemonApiDataList != null) {

            for (i in pokemonApiDataList.results.indices) {

                var newElement = PokemonListElement(
                    start + i + 1,
                    pokemonApiDataList.results[i].name.capitalize(),
                    ""
                )
                resultArray.add(newElement)

            }
        }

        return PokemonsList(
            pokemonApiDataList.count,
            pokemonApiDataList.next,
            pokemonApiDataList.previous,
            resultArray
        )
    }
}
