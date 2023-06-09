package com.example.mvvm.ui.adapter.viewholder

import android.view.ViewGroup
import com.example.mvvm.data.enumtype.State
import com.example.mvvm.data.model.MovieItem
import com.example.mvvm.databinding.ItemBigMovieBinding
import com.example.mvvm.extension.loadImage
import com.example.mvvm.extension.onVisibility
import com.example.mvvm.ui.base.BaseViewHolder

class BigMovieViewHolder(
    private val binding: ItemBigMovieBinding,
    private val actionMoveDetail: (Int) -> Unit,
    private val actionReload: () -> Unit,
) : BaseViewHolder<MovieItem>(binding.root) {

    override fun bind(model: MovieItem?, state: State) {
        binding.apply {
            when (state) {
                State.LOADING -> {
                    (container as ViewGroup).onVisibility(loading)
                    root.setOnClickListener(null)
                }
                State.ERROR -> {
                    (container as ViewGroup).onVisibility(reload)

                    root.setOnClickListener {
                        actionReload.invoke()
                    }
                }
                else -> {
                    (container as ViewGroup).onVisibility(title, image)

                    model?.let {
                        title.text = it.title

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