package com.jca.pokedex.ui.base

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.jca.pokedex.R
import kotlinx.android.synthetic.main.activity_item_list.*

open class BaseFragment : Fragment(), BaseView{
    override fun showDefaultError() {
        Toast.makeText(context, R.string.defaultError, Toast.LENGTH_LONG).show()
    }

    override fun showDefaultNetworkError() {
        Toast.makeText(context, R.string.defaultNetworkError, Toast.LENGTH_LONG).show()
    }

    override fun showDefaultRequestError() {
        Toast.makeText(context, R.string.requestFailure, Toast.LENGTH_LONG).show()
    }

    override fun showError(erroStringId: Int) {
        Toast.makeText(context, erroStringId, Toast.LENGTH_LONG).show()
    }

    override fun showProgress() {
        (activity as BaseActivity).showProgress()
    }

    override fun hideProgress() {
        (activity as BaseActivity).hideProgress()
    }

    override fun setWindowTitle(title: String) {
        toolbar.title = title
    }
}