package com.jca.pokedex.ui.base

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.jca.pokedex.R
import kotlinx.android.synthetic.main.activity_item_list.*

open class BaseCompatActivity : AppCompatActivity(),BaseActivity, BaseView{
    lateinit var progressBar: ProgressBar

    fun initializeBase() {
        // Get progressBar instance
        progressBar = topProgressBar
    }
    override fun showDefaultError() {
        Toast.makeText(this, R.string.defaultError, Toast.LENGTH_LONG).show()
    }

    override fun showDefaultNetworkError() {
        Toast.makeText(this, R.string.defaultNetworkError, Toast.LENGTH_LONG).show()
    }

    override fun showDefaultRequestError() {
        Toast.makeText(this, R.string.requestFailure, Toast.LENGTH_LONG).show()
    }

    override fun showError(erroStringId: Int) {
        Toast.makeText(this, erroStringId, Toast.LENGTH_LONG).show()
    }

    override fun showProgress(){
        progressBar!!.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBar!!.visibility = View.INVISIBLE
    }

    override fun setWindowTitle(title: String) {
        toolbar.title = title
    }
}