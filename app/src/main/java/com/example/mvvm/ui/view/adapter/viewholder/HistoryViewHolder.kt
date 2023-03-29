package com.example.mvvm.ui.view.adapter.viewholder

import com.bumptech.glide.Glide
import com.example.mvvm.data.enumtype.State
import com.example.mvvm.data.model.MovieDetail
import com.example.mvvm.databinding.ItemHistoryBinding
import com.example.mvvm.extension.orW600xH900
import com.example.mvvm.extension.orZero
import com.example.mvvm.extension.rating
import com.example.mvvm.ui.base.BaseViewHolder

class HistoryViewHolder(
    private val binding: ItemHistoryBinding,
    private val actionMoveDetail: (Int) -> Unit
) : BaseViewHolder<MovieDetail>(binding.root) {
    override fun bind(model: MovieDetail?, position: Int, state: State) {
        binding.apply {
            model?.let {model ->
                title.text = model.title
                releaseDate.text = model.releaseDate
                voteAverage.rating = model.voteAverage.rating()

                Glide.with(context)
                    .load(model.posterPath.orW600xH900())
                    .into(binding.image)

                root.setOnClickListener {
                    actionMoveDetail.invoke(model.id.orZero())
                }
            }
        }
    }
}