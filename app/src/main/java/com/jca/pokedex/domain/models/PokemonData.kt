package com.jca.pokedex.domain.models

import me.sargunvohra.lib.pokekotlin.model.*

data class PokemonData(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val types: ArrayList<String>,
    val sprite: String?
){
    fun getTypesAsString():String{
        var typesString = ""

        if (types != null && types.isNotEmpty())
        {
            for (type in types){
                typesString += " $type,"
            }
            typesString = typesString.substring(0,typesString.length-1) // Eliminate last ","
        }

        return typesString
    }
}