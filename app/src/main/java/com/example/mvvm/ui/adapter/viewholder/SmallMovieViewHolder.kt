package com.example.mvvm.ui.adapter.viewholder

import android.view.ViewGroup
import com.example.mvvm.R
import com.example.mvvm.data.enumtype.State
import com.example.mvvm.data.model.MovieItem
import com.example.mvvm.databinding.ItemSmallMovieBinding
import com.example.mvvm.extension.loadImage
import com.example.mvvm.extension.onVisibility
import com.example.mvvm.ui.base.BaseViewHolder

class SmallMovieViewHolder(
    private val binding: ItemSmallMovieBinding,
    private val actionMoveDetail: (Int) -> Unit,
    private val actionReload: () -> Unit,
    private val actionLoadMore: () -> Unit
) : BaseViewHolder<MovieItem>(binding.root) {

    override fun bind(model: MovieItem?, state: State) {
        binding.apply {
            image.apply {
                val margin = context.resources.getDimensionPixelSize(R.dimen.margin)
                layoutParams.width = ((context.resources.displayMetrics.widthPixels - margin * 3) / 2.5).toInt()
            }

            when (state) {
                State.LOADING -> {
                    (container as ViewGroup).onVisibility(loading)
                    root.setOnClickListener(null)
                }
                State.ERROR-> {
                    (container as ViewGroup).onVisibility(reload)

                    root.setOnClickListener {
                        actionReload.invoke()
                    }
                }
                State.LOAD_MORE -> {
                    (container as ViewGroup).onVisibility(loadMore)

                    root.setOnClickListener {
                        actionLoadMore.invoke()
                    }
                }
                else -> {
                    (container as ViewGroup).onVisibility(title, image)
                    model?.let { model ->
                        title.text = model.title

                        image.loadImage(model.imagePath)

                        root.setOnClickListener {
                            actionMoveDetail.invoke(model.id)
                        }
                    }
                }
            }
        }
    }
}