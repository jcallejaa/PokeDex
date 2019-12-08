package com.jca.pokedex.ui.home

import com.jca.pokedex.R
import com.jca.pokedex.domain.interactors.GetPokemonListUseCase
import com.jca.pokedex.domain.models.PokemonListElement
import com.jca.pokedex.domain.models.PokemonsList

class HomePresenterImpl : HomePresenter {

    private lateinit var view: HomeView

    private lateinit var list: ArrayList<PokemonListElement>

    override fun setView(view: HomeView) {
        this.view = view
    }

    override fun setSearchString(searchString: String) {
        filterData(searchString)
    }

    override fun getPokemonList() {
        view.showProgress()
        GetPokemonListUseCase.execute(object : GetPokemonListUseCase.UseCaseListener {
            override fun notifyPokemonListReady(incomingList: PokemonsList) {
                list = incomingList.pokemonList!!
                view.setListElements(list)
                view.hideProgress()
            }

            override fun notifyPokemonListError() {
                view.hideProgress()
                view.showDefaultRequestError()
            }
        })
    }

    private fun filterData(newText: String) {

        var filtredData = list.filter { it.name.toUpperCase().contains(newText.toUpperCase()) }
        view.setListElements(ArrayList(filtredData))
    }

    override fun setPokemonSelected(pokemon: PokemonListElement) {
        view.openDetailFor(pokemon)
    }


}