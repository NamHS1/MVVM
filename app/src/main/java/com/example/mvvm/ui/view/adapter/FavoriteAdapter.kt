package com.example.mvvm.ui.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm.R
import com.example.mvvm.data.database.entity.Favorite
import com.example.mvvm.databinding.ItemFavoriteBinding
import com.example.mvvm.ui.base.BaseViewHolder
import com.example.mvvm.ui.view.adapter.viewholder.FavoriteViewHolder

class FavoriteAdapter(
    context: Context,
    private val actionMoveDetail: (Int) -> Unit,
    private val actionFavorite: (Int, Favorite) -> Unit
) : RecyclerView.Adapter<BaseViewHolder<Favorite>>() {

    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    var favorites: MutableList<Favorite> = mutableListOf()
        set(value) {
            field = value
            notifyItemRangeInserted(favorites.size, field.size)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Favorite> {
        val binding = DataBindingUtil.inflate<ItemFavoriteBinding>(
            layoutInflater,
            R.layout.item_favorite,
            parent,
            false
        )
        return FavoriteViewHolder(
            binding = binding,
            actionMoveDetail = actionMoveDetail,
            actionFavorite = actionFavorite
        )
    }

    override fun getItemCount() = favorites.size

    override fun onBindViewHolder(holder: BaseViewHolder<Favorite>, position: Int) {
        holder.bind(favorites[position], position)
    }
}