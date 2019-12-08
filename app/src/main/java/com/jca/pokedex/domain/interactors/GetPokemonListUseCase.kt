package com.jca.pokedex.domain.interactors

import com.jca.pokedex.data.remote.PokeApiManager
import com.jca.pokedex.domain.models.PokemonsList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class GetPokemonListUseCase {
    companion object {
        fun execute(callback: UseCaseListener) {
            var list: PokemonsList
            GlobalScope.launch(Dispatchers.Default) {
                //Do operations on some thread async
                list = PokeApiManager.instance.getPokemonsList()

                withContext(Dispatchers.Main) {
                    // Perform operations on the main thread
                    if (list != null) {
                        callback.notifyPokemonListReady(list)
                    } else {
                        callback.notifyPokemonListError()
                    }
                }
            }
        }
    }
     interface UseCaseListener{
         fun notifyPokemonListReady(list: PokemonsList)
         fun notifyPokemonListError()
     }
}