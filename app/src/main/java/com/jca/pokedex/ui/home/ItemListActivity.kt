package com.jca.pokedex

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.RecyclerView
import com.jca.pokedex.domain.models.PokemonListElement
import com.jca.pokedex.ui.base.BaseCompatActivity
import com.jca.pokedex.ui.home.HomePresenter
import com.jca.pokedex.ui.home.HomePresenterImpl
import com.jca.pokedex.ui.home.HomeView
import kotlinx.android.synthetic.main.activity_item_list.*
import kotlinx.android.synthetic.main.item_list.*
import kotlinx.android.synthetic.main.item_list_content.view.*

class ItemListActivity : BaseCompatActivity(), HomeView  {

    private var twoPane: Boolean = false

    // This could be inyected via RetroFit to avoid instantiating.
    // I'll do that if I've enough time
    var presenter : HomePresenter = HomePresenterImpl()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_list)

        setSupportActionBar(toolbar)
        super.initializeBase()


        presenter.setView(this) // SetView to presenter

        if (item_detail_container != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            twoPane = true
        }

        presenter.getPokemonList()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val searchItem = menu.findItem(R.id.app_bar_search)
        val searchView = searchItem.actionView as SearchView
        searchView.queryHint = getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object :  SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                presenter.setSearchString(newText)
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                // Nothing to do because all is done in onQueryTextChange()
                return false
            }

        })

        return true
    }

    // ---- HomeView implementedMethods ----
    private fun setPokemonSelected(item: PokemonListElement) {
        presenter.setPokemonSelected(item)
    }

    override fun setListElements(pokemonsList: ArrayList<PokemonListElement>) {
        setupPokemonsRecyclerView(item_list,pokemonsList)
    }


    override fun openDetailFor(pokemon : PokemonListElement){
        if (twoPane) {
            val fragment = ItemDetailFragment().apply {
                arguments = Bundle().apply {
                    putInt(ItemDetailFragment.ARG_ITEM_ID, pokemon.id)
                    putString(ItemDetailFragment.ARG_ITEM_NAME, pokemon.name)
                }
            }
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.item_detail_container, fragment)
                .commit()
        } else {
            val intent = Intent(this, ItemDetailActivity::class.java).apply {
                putExtra(ItemDetailFragment.ARG_ITEM_ID, pokemon.id)
                putExtra(ItemDetailFragment.ARG_ITEM_NAME, pokemon.name)
            }
            startActivity(intent)
        }
    }

    private fun setupPokemonsRecyclerView(recyclerView: RecyclerView, list: ArrayList<PokemonListElement>) {
        recyclerView.adapter = SimpleItemRecyclerViewAdapter(this, list, twoPane)
    }

    // ---- Other Classes ----

    class SimpleItemRecyclerViewAdapter(
        private val parentActivity: ItemListActivity,
        private val values: List<PokemonListElement>,
        private val twoPane: Boolean) : RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {

        private val onClickListener: View.OnClickListener

        init {
            onClickListener = View.OnClickListener { v ->
                val item = v.tag as PokemonListElement
                // Send selected element to activity in order to communicate with the pressenter
                parentActivity.setPokemonSelected(item)
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_content, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = values[position]
            holder.pokemonName.text = item.name

            with(holder.itemView) {
                tag = item
                setOnClickListener(onClickListener)
            }
        }

        override fun getItemCount() = values.size

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val pokemonName: TextView = view.pokemonName
        }
    }


}
