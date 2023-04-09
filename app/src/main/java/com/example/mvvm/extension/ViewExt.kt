package com.example.mvvm.extension

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.forEach
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mvvm.R
import com.example.mvvm.data.enumtype.MovieType
import com.example.mvvm.databinding.ItemHomeStateBinding

fun ViewGroup.onVisibility(vararg views: View) {
    this.forEach {
        view@ for (view in views) {
            if (it.id == view.id) {
                it.visibility = View.VISIBLE
                break@view
            } else {
                it.visibility = View.INVISIBLE
            }
        }
    }
}

fun View.showKeyBoard() {
    val imm: InputMethodManager =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

fun View.hideKeyBoard() {
    val imm: InputMethodManager =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(this.windowToken, 0)
}

fun AppCompatImageView.loadImage(path: String) {
    Glide.with(context)
        .load(path)
        .into(this)
}

fun <T : Any, V : RecyclerView.ViewHolder> PagingDataAdapter<T, V>.addLoadStateListener(
    root: ItemHomeStateBinding,
    type: MovieType = MovieType.POPULAR,
    initIndicator: (Int) -> Unit
) {
    this.addLoadStateListener {

        root.root.apply {
            val width: Int?
            val ratio: Float?
            val margin = context.resources.getDimensionPixelSize(R.dimen.margin)
            when (type) {
                MovieType.UPCOMING -> {
                    width = context.resources.displayMetrics.widthPixels - margin * 2
                    ratio = 0.56F
                }
                else-> {
                    width =  ((context.resources.displayMetrics.widthPixels - margin * 3) / 2.5).toInt()
                    ratio = 1.5F
                }
            }
            layoutParams.height = (width * ratio).toInt()
        }


        if (it.refresh is LoadState.Loading) {
            root.loading.isVisible = true
            root.reload.isVisible = false
        }

        if (it.refresh is LoadState.Error) {
            root.loading.isVisible = false
            root.reload.isVisible = true
            root.root.setOnClickListener {
                this.retry()
            }
        }

        val itemCount = this.itemCount
        if (itemCount > 0 && it.refresh is LoadState.NotLoading) {
            initIndicator.invoke(itemCount)
        }
    }
}