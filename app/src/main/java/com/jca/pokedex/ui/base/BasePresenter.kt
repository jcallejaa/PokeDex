package com.jca.pokedex.ui.base

import com.jca.pokedex.ui.home.HomeView

interface BasePresenter<T> {
    fun setView(view: T)
}