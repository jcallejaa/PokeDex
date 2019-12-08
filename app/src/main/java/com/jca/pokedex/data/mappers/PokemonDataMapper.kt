package com.jca.pokedex.data.mappers

import com.jca.pokedex.domain.models.PokemonData
import me.sargunvohra.lib.pokekotlin.model.Pokemon

class PokemonDataMapper {
    companion object {
        private var instance: PokemonDataMapper = PokemonDataMapper()

        fun getInstance(): PokemonDataMapper {
            return instance
        }
    }

    fun map(pokemonApiData : Pokemon): PokemonData? {

        if (pokemonApiData != null){

            var types = ArrayList<String>()
            for (type in pokemonApiData.types){
                types.add(type.type.name.capitalize())
            }


            return PokemonData(
                pokemonApiData.id,
                pokemonApiData.name.capitalize(),
                pokemonApiData.height,
                pokemonApiData.weight,
                types,
                pokemonApiData.sprites.frontDefault
            )
        } else return null
    }
}