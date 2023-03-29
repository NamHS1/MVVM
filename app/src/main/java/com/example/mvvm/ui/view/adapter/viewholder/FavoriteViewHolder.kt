package com.example.mvvm.ui.view.adapter.viewholder

import com.bumptech.glide.Glide
import com.example.mvvm.data.database.entity.Favorite
import com.example.mvvm.data.enumtype.State
import com.example.mvvm.databinding.ItemFavoriteBinding
import com.example.mvvm.ui.base.BaseViewHolder

class FavoriteViewHolder(
    private val binding: ItemFavoriteBinding,
    private val actionMoveDetail: (Int) -> Unit,
    private val actionFavorite: (Int, Favorite) -> Unit
) : BaseViewHolder<Favorite>(binding.root) {
    override fun bind(model: Favorite?, position: Int, state: State) {
        binding.apply {
            model?.let { model ->
                title.text = model.title
                releaseDate.text = model.release
                voteAverage.rating = model.voteAverage

                Glide.with(context)
                    .load(model.posterPath)
                    .into(binding.image)

                root.setOnClickListener {
                    actionMoveDetail.invoke(model.id)
                }

                favourite.setOnClickListener {
                    actionFavorite.invoke(position, model)
                }
            }
        }
    }
}