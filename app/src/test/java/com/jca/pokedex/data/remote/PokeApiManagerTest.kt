package com.jca.pokedex.data.remote

import com.nhaarman.mockitokotlin2.any
import me.sargunvohra.lib.pokekotlin.client.PokeApiClient
import me.sargunvohra.lib.pokekotlin.model.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class PokeApiManagerTest {

    val POKEMON_NAME = "PokeTest"

    var pokeApiManager = PokeApiManager()

    lateinit var pokemonsListData: NamedApiResourceList
    lateinit var pokemonsList: List<NamedApiResource>
    lateinit var pokemon: Pokemon
    lateinit var pokemonTypes: ArrayList<PokemonType>

    @Mock
    val pokeApiClient = Mockito.mock(PokeApiClient::class.java)

    @Mock
    val namedApiResource = Mockito.mock(NamedApiResource::class.java)


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        initMocks()

        pokeApiManager.setPokeApiClient(pokeApiClient) // Set a mock to intercept behavior
        Mockito.`when`(pokeApiClient.getPokemonList(any(), any())).thenReturn(pokemonsListData)
        Mockito.`when`(pokeApiClient.getPokemon(any())).thenReturn(pokemon)
    }

    fun initMocks() {

        //Pokemon Detail

        pokemonTypes = ArrayList<PokemonType>()
        pokemonTypes.add(PokemonType(0, NamedApiResource("type 0", "", 0)))
        pokemonTypes.add(PokemonType(1, NamedApiResource("type 1", "", 1)))
        pokemonTypes.add(PokemonType(2, NamedApiResource("type 2", "", 2)))

        var pokemonSprites = PokemonSprites(
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
        )


        pokemon = Pokemon(
            1024,
            POKEMON_NAME,
            0,
            15,
            false,
            0,
            1024,
            namedApiResource,
            ArrayList<PokemonAbility>(),
            ArrayList<NamedApiResource>(),
            ArrayList<VersionGameIndex>(),
            ArrayList<PokemonHeldItem>(),
            ArrayList<PokemonMove>(),
            ArrayList<PokemonStat>(),
            pokemonTypes,
            pokemonSprites
        )

        // Pokemons List
        val NUM_ELEMENTS = 5;
        pokemonsList = getDummyNamedApiResourceList(NUM_ELEMENTS)
        pokemonsListData = NamedApiResourceList(NUM_ELEMENTS, "", "", pokemonsList)
    }

    fun getDummyNamedApiResourceList(numElements: Int): List<NamedApiResource> {
        var array = ArrayList<NamedApiResource>()

        for (i in 1..numElements) {
            array.add(getDummyNamedApiResource("" + i, "", i))
        }

        return array
    }

    fun getDummyNamedApiResource(name: String, category: String, id: Int): NamedApiResource {
        return NamedApiResource(name, category, id)
    }

    @Test
    fun test_getPokemonData() {
        var pokemonData = pokeApiManager.getPokemonData(0)

        assertEquals(pokemonData!!.name, POKEMON_NAME)
        assertTrue(pokemonData!!.types.contains("Type 0"))
    }

    @Test
    fun test_getPokemonsList() {
        assertEquals(pokeApiManager.getPokemonsList().count, 5)
        assertEquals(pokeApiManager.getPokemonsList().pokemonList!!.size, 5)
    }


    @After
    fun tearDown() {
    }
}