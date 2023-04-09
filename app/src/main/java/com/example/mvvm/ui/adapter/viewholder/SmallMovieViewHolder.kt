package com.example.mvvm.ui.adapter.viewholder

import com.example.mvvm.R
import com.example.mvvm.databinding.ItemSmallMovieBinding
import com.example.mvvm.extension.loadImage
import com.example.mvvm.ui.base.BaseViewHolder
import com.example.mvvm.ui.model.MovieResponse

class SmallMovieViewHolder(
    private val binding: ItemSmallMovieBinding,
    private val actionMoveDetail: (Int) -> Unit
) : BaseViewHolder<MovieResponse>(binding.root) {

    override fun bind(t: MovieResponse?) {
        binding.apply {
            image.apply {
                val margin = context.resources.getDimensionPixelSize(R.dimen.margin)
                layoutParams.width =
                    ((context.resources.displayMetrics.widthPixels - margin * 3) / 2.5).toInt()
            }
            t?.let { model ->
                title.text = model.title

                image.loadImage(model.posterPath)

                root.setOnClickListener {
                    actionMoveDetail.invoke(model.id)
                }
            }
        }
    }
}