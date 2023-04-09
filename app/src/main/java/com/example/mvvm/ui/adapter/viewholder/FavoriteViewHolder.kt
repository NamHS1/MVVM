package com.example.mvvm.ui.adapter.viewholder

import com.example.mvvm.data.database.entity.Favorite
import com.example.mvvm.databinding.ItemFavoriteBinding
import com.example.mvvm.extension.loadImage
import com.example.mvvm.ui.base.BaseViewHolder

class FavoriteViewHolder(
    private val binding: ItemFavoriteBinding,
    private val actionMoveDetail: (Int) -> Unit,
    private val actionFavorite: (Int, Favorite) -> Unit
) : BaseViewHolder<Favorite>(binding.root) {
    override fun bind(t: Favorite?) {
        binding.apply {
            t?.let { favorite ->
                title.text = favorite.title
                releaseDate.text = favorite.release
                voteAverage.rating = favorite.voteAverage

                image.loadImage(favorite.posterPath)

                root.setOnClickListener {
                    actionMoveDetail.invoke(favorite.id)
                }

                favourite.setOnClickListener {
                    actionFavorite.invoke(bindingAdapterPosition, favorite)
                }
            }
        }
    }
}