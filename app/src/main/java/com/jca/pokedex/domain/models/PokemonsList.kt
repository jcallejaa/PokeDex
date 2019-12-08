package com.jca.pokedex.domain.models

data class PokemonsList(
    var count: Int,
    var nextPage: String ?,
    var prefious: String ?,
    var pokemonList: ArrayList<PokemonListElement>?
)