package com.jca.pokedex.data.utils

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class ImagesLoaderHandler {

    companion object {
        fun loadImage(view: ImageView, imageUrl: String) {
            Glide.with(view.context)
                .load(imageUrl)
                .into(view)
        }

        fun loadImage(view: ImageView, imageUrl: String, listener: ImageLoadCallback) {
            Glide.with(view.context)
                .load(imageUrl)
                .listener(object : RequestListener<Drawable> {
                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: com.bumptech.glide.load.DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        listener.notifyImageLoaded(resource)
                        return true
                    }

                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        listener.notifyErrorLoadingImage()
                        return false
                    }

                })
                .into(view)
        }
    }


    /** This interface allows to simplify and make more mantenible in separated layers, this class.
     * Using this interface instead of Glide's one, makes more unengaged the code to manage loading images.
     * */
    interface ImageLoadCallback {
        fun notifyImageLoaded(image: Drawable?)
        fun notifyErrorLoadingImage()
    }
}