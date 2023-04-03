package com.example.mvvm.ui.adapter.viewholder

import androidx.appcompat.widget.AppCompatCheckBox
import com.example.mvvm.data.database.entity.Favorite
import com.example.mvvm.data.enumtype.State
import com.example.mvvm.data.model.SearchItem
import com.example.mvvm.databinding.ItemSearchBinding
import com.example.mvvm.extension.loadImage
import com.example.mvvm.ui.base.BaseViewHolder

class SearchViewHolder(
    private val binding: ItemSearchBinding,
    private val listFavorite: List<Favorite>,
    private val actionMoveDetail: (Int) -> Unit,
    private val actionFavorite: (Favorite, Boolean) -> Unit
) : BaseViewHolder<SearchItem>(binding.root) {
    override fun bind(model: SearchItem?, state: State) {
        model?.let { searchItem ->
            binding.apply {
                image.loadImage(searchItem.imagePath)
                title.text = searchItem.title
                releaseDate.text = searchItem.release
                voteAverage.rating = searchItem.voteAverage

                val favorite = Favorite(
                    id = searchItem.id,
                    title = searchItem.title,
                    posterPath = searchItem.imagePath,
                    release = searchItem.release,
                    voteAverage = searchItem.voteAverage
                )
                favourite.isChecked = listFavorite.find { it.id == favorite.id } != null
                favourite.setOnClickListener {
                    actionFavorite.invoke(favorite, (it as AppCompatCheckBox).isChecked)
                }

                image.setOnClickListener {
                    actionMoveDetail.invoke(searchItem.id)
                }
                background.setOnClickListener {
                    actionMoveDetail.invoke(searchItem.id)
                }
            }
        }
    }
}