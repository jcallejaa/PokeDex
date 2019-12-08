package com.jca.pokedex.ui.detail

import android.webkit.URLUtil
import com.jca.pokedex.domain.interactors.GetPokemonDataUseCase
import com.jca.pokedex.domain.models.PokemonData


class DetailPressenterImpl : DetailPresenter {

    private lateinit var view: DetailView

    private var pokemonData: PokemonData? = null

    override fun setPokemonId(id: Int?) {
        if (id != null && id > 0) {
            view.showProgress()
            requestPokemonData(id)
        } else {
            view.showErrorInvalidPokemonId()
        }
    }

    private fun requestPokemonData(pokemonId: Int) {

        GetPokemonDataUseCase.execute(pokemonId, object : GetPokemonDataUseCase.UseCaseListener {

            override fun notifyPokemonDataReady(data: PokemonData) {

                view.setPokemonName(data.name)
                view.setPokemonHeight("" + "%.2f".format((data.height / 10f)) + "m") // Conversion to make measure real in pokemon units)
                view.setPokemonWeight("" + "%.2f".format((data.weight / 10f)) + "Kg") // Conversion to make measure real in pokemon units)
                view.setPokemonTypesString(data.getTypesAsString())
                if (URLUtil.isValidUrl(data.sprite!!)) {
                    view.showPokemonImage(data.sprite!!)
                }
                view.hideProgress()

            }

            override fun notifyPokemonDataError() {

                view.showDefaultRequestError()
                view.hideProgress()

            }
        })
    }

    override fun setView(view: DetailView) {
        this.view = view
    }
}