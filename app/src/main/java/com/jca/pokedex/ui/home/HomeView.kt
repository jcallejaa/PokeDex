package com.jca.pokedex.ui.home

import com.jca.pokedex.domain.models.PokemonListElement
import com.jca.pokedex.ui.base.BaseView

interface HomeView : BaseView{
    fun setListElements(pokemonsList: ArrayList<PokemonListElement>)
    fun openDetailFor(pokemonData:PokemonListElement)
}