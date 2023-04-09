package com.example.mvvm.ui.adapter.viewholder

import com.example.mvvm.databinding.ItemBigMovieBinding
import com.example.mvvm.extension.loadImage
import com.example.mvvm.ui.base.BaseViewHolder
import com.example.mvvm.ui.model.MovieResponse

class BigMovieViewHolder(
    private val binding: ItemBigMovieBinding,
    private val actionMoveDetail: (Int) -> Unit
) : BaseViewHolder<MovieResponse>(binding.root) {

    override fun bind(t: MovieResponse?) {
        binding.apply {
            t?.let { movieResponse ->
                title.text = movieResponse.title
                image.loadImage(movieResponse.backdropPath)
                root.setOnClickListener {
                    actionMoveDetail.invoke(movieResponse.id)
                }
            }
        }
    }
}