package com.jca.pokedex.data

import com.jca.pokedex.domain.models.PokemonData
import com.jca.pokedex.domain.models.PokemonsList

interface PokeRepository {
    fun getPokemonData(pokemonId: Int): PokemonData?
    fun getPokemonsList() : PokemonsList
    fun getPokemonsList(start: Int, end:Int): PokemonsList
}