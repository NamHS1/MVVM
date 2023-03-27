package com.example.mvvm.ui.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm.R
import com.example.mvvm.data.model.MovieDetail
import com.example.mvvm.databinding.ItemFavoriteBinding
import com.example.mvvm.ui.base.BaseViewHolder
import com.example.mvvm.ui.view.adapter.viewholder.FavoriteViewHolder

class FavoriteAdapter(
    context: Context,
    private val actionMoveDetail: (Int) -> Unit,
    private val actionFavorite: (Int, Int) -> Unit
) : RecyclerView.Adapter<BaseViewHolder<MovieDetail>>() {

    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    var movies: MutableList<MovieDetail> = mutableListOf()
        set(value) {
            field = value
            notifyItemRangeInserted(0, field.size)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<MovieDetail> {
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

    override fun getItemCount() = movies.size

    override fun onBindViewHolder(holder: BaseViewHolder<MovieDetail>, position: Int) {
        holder.bind(movies[position], position)
    }
}