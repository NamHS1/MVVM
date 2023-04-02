package com.example.mvvm.ui.view.adapter.viewholder

import com.example.mvvm.data.enumtype.State
import com.example.mvvm.data.model.MovieDetail
import com.example.mvvm.databinding.ItemHistoryBinding
import com.example.mvvm.extension.loadImage
import com.example.mvvm.extension.orW600xH900
import com.example.mvvm.extension.orZero
import com.example.mvvm.extension.rating
import com.example.mvvm.ui.base.BaseViewHolder

class HistoryViewHolder(
    private val binding: ItemHistoryBinding,
    private val actionMoveDetail: (Int) -> Unit
) : BaseViewHolder<MovieDetail>(binding.root) {
    override fun bind(model: MovieDetail?, state: State) {
        binding.apply {
            model?.let {model ->
                title.text = model.title
                releaseDate.text = model.releaseDate
                voteAverage.rating = model.voteAverage.rating()

                image.loadImage(model.posterPath.orW600xH900())

                root.setOnClickListener {
                    actionMoveDetail.invoke(model.id.orZero())
                }
            }
        }
    }
}