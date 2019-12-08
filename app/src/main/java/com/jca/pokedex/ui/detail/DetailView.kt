package com.jca.pokedex.ui.detail

import com.jca.pokedex.ui.base.BaseView

interface DetailView: BaseView {
    fun setPokemonName(name:String)
    fun setPokemonHeight(height:String)
    fun setPokemonWeight(weight:String)
    fun setPokemonTypesString(types:String)
    fun showPokemonImage(imageUrl: String)
    fun showErrorInvalidPokemonId()
    fun showSpriteLoading()
    fun hideSpriteLoading()

}