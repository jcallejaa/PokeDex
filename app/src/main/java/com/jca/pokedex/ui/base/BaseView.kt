package com.jca.pokedex.ui.base

interface BaseView {
    fun setWindowTitle(title: String)
    fun showDefaultError()
    fun showDefaultNetworkError()
    fun showDefaultRequestError()
    fun showError(errorStringId: Int)
    fun showProgress()
    fun hideProgress()
}