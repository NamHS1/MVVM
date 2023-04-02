package com.example.mvvm.ui.view.adapter.viewholder

import com.example.mvvm.data.database.entity.Favorite
import com.example.mvvm.data.enumtype.State
import com.example.mvvm.databinding.ItemFavoriteBinding
import com.example.mvvm.extension.loadImage
import com.example.mvvm.ui.base.BaseViewHolder

class FavoriteViewHolder(
    private val binding: ItemFavoriteBinding,
    private val actionMoveDetail: (Int) -> Unit,
    private val actionFavorite: (Int, Favorite) -> Unit
) : BaseViewHolder<Favorite>(binding.root) {
    override fun bind(model: Favorite?, state: State) {
        binding.apply {
            model?.let { model ->
                title.text = model.title
                releaseDate.text = model.release
                voteAverage.rating = model.voteAverage

                image.loadImage(model.posterPath)

                root.setOnClickListener {
                    actionMoveDetail.invoke(model.id)
                }

                favourite.setOnClickListener {
                    actionFavorite.invoke(adapterPosition, model)
                }
            }
        }
    }
}