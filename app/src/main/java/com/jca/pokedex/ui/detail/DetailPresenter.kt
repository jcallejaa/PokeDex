package com.jca.pokedex.ui.detail

import com.jca.pokedex.ui.base.BasePresenter
import com.jca.pokedex.ui.home.HomeView

interface DetailPresenter : BasePresenter<DetailView> {
    fun setPokemonId(id: Int?)
}