package com.jca.pokedex

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.airbnb.lottie.LottieAnimationView
import com.jca.pokedex.data.utils.ImagesLoaderHandler
import com.jca.pokedex.ui.base.BaseFragment
import com.jca.pokedex.ui.detail.DetailPresenter
import com.jca.pokedex.ui.detail.DetailPressenterImpl
import com.jca.pokedex.ui.detail.DetailView
import kotlinx.android.synthetic.main.item_detail.view.*

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a [ItemListActivity]
 * in two-pane mode (on tablets) or a [ItemDetailActivity]
 * on handsets.
 */
class ItemDetailFragment : BaseFragment(), DetailView {

    companion object {
        const val ARG_ITEM_ID = "pokemon_id"
        const val ARG_ITEM_NAME = "pokemon_name"
    }

    // This could be inyected via RetroFit to avoid instantiating.
    // I'll do that if I've enough time
    private var detailPresenter: DetailPresenter = DetailPressenterImpl()

    private var pokemonId: Int = 0

    private lateinit var tvName: TextView
    private lateinit var tvHeight: TextView
    private lateinit var tvWeight: TextView
    private lateinit var ivImage: ImageView
    private lateinit var tvTypes: TextView
    private lateinit var pbSpriteLoading: LottieAnimationView // To show a lottie animation


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailPresenter.setView(this)

        arguments?.getInt(ARG_ITEM_ID)?.let {
            pokemonId = it
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.item_detail, container, false)

        tvName = rootView.tvName
        tvHeight = rootView.tvHeight
        tvWeight = rootView.tvWeight
        ivImage = rootView.sprite
        tvTypes = rootView.tvTypes
        pbSpriteLoading = rootView.spriteLoading

        detailPresenter.setPokemonId(pokemonId)


        return rootView
    }


    // ---- DetailView implementedMethods ----

    override fun setPokemonName(name: String) {
        tvName.text = name
    }

    override fun setPokemonHeight(height: String) {
        tvHeight.text = height
    }

    override fun setPokemonWeight(weight: String) {
        tvWeight.text = weight
    }

    override fun setPokemonTypesString(types: String) {
        tvTypes.text = types
    }

    override fun showPokemonImage(imageUrl: String) {
        showSpriteLoading()
        // This "postDelayed" call is used to make lottie progress animation visible.
        // if not set, the image is loaded un less than half second and is not shown
        // Of course in normal situations, this Handler can be eliminated and called
        // ImagesLoaderHandler directly
        Handler().postDelayed(
            {
                ImagesLoaderHandler.loadImage(
                    ivImage,
                    imageUrl,
                    object : ImagesLoaderHandler.ImageLoadCallback {
                        override fun notifyImageLoaded(image: Drawable?) {
                            ivImage.setImageDrawable(image)
                            hideSpriteLoading()
                        }

                        override fun notifyErrorLoadingImage() {
                            hideSpriteLoading()
                        }
                    })
            }, 1000
        )

    }

    override fun showErrorInvalidPokemonId() {
        Toast.makeText(
            this@ItemDetailFragment.context,
            R.string.errorInvalidPokemonId,
            Toast.LENGTH_LONG
        ).show()
    }

    override fun showSpriteLoading() {
        pbSpriteLoading.resumeAnimation()
        pbSpriteLoading.visibility = View.VISIBLE

    }

    override fun hideSpriteLoading() {
        pbSpriteLoading.pauseAnimation()
        pbSpriteLoading.visibility = View.GONE
    }
}
