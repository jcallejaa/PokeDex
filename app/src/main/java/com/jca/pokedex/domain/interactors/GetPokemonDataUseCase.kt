package com.jca.pokedex.domain.interactors

import com.jca.pokedex.data.remote.PokeApiManager
import com.jca.pokedex.domain.models.PokemonData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class GetPokemonDataUseCase {
    companion object {
        fun execute(pokeminId: Int, callback: UseCaseListener) {
            var data: PokemonData
            GlobalScope.launch(Dispatchers.Default) {
                //Do operations on some thread async
                data = PokeApiManager.instance.getPokemonData(pokeminId)!!

                withContext(Dispatchers.Main) {
                    // Perform operations on the main thread
                    if (data != null) {
                        callback.notifyPokemonDataReady(data)
                    } else {
                        callback.notifyPokemonDataError()
                    }
                }
            }
        }
    }

    interface UseCaseListener {
        fun notifyPokemonDataReady(data: PokemonData)
        fun notifyPokemonDataError()
    }
}