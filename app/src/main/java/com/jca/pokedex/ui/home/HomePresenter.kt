package com.jca.pokedex.ui.home

import com.jca.pokedex.domain.models.PokemonListElement
import com.jca.pokedex.ui.base.BasePresenter

interface HomePresenter : BasePresenter<HomeView> {
    fun setSearchString(searchString: String)
    fun getPokemonList()
    fun setPokemonSelected(pokemon: PokemonListElement)
}