package com.genericandwildcard.coronafinder.app.core.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.view.children
import androidx.core.view.isVisible
import com.genericandwildcard.coronafinder.app.R

class StateView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    init {
        LayoutInflater.from(context).inflate(R.layout.state, this)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        showLoading()
    }

    fun showError(errorMessage: String = context.getString(R.string.error)) {
        this.children.forEach { view ->
            if (view.id == R.id.errorView) {
                view.isVisible = true
                (view as TextView).text = errorMessage
            } else {
                view.isVisible = false
            }
        }
    }

    fun showLoading() {
        this.children.forEach { view ->
            if (view.id == R.id.loadingView) {
                view.isVisible = true
            } else {
                view.isVisible = false
            }
        }
    }

    fun showNoData(message: String = context.getString(R.string.no_data)) {
        this.children.forEach { view ->
            if (view.id == R.id.noResult) {
                view.isVisible = true
                (view as TextView).text = message
            } else {
                view.isVisible = false
            }
        }
    }

    fun showSuccess() {
        this.children.forEach { view ->
            if (view.id == R.id.noResult || view.id == R.id.loadingView || view.id == R.id.errorView) {
                view.isVisible = false
            } else {
                view.isVisible = true
            }
        }
    }
}