package com.example.mvvm.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.mvvm.R
import com.example.mvvm.data.database.entity.Favorite
import com.example.mvvm.databinding.ItemSearchBinding
import com.example.mvvm.ui.adapter.viewholder.SearchViewHolder
import com.example.mvvm.ui.base.BaseViewHolder
import com.example.mvvm.ui.model.MovieResponse

class SearchAdapter(
    private val actionMoveDetail: (Int) -> Unit,
    private val actionFavorite: (Favorite, Boolean) -> Unit
) : PagingDataAdapter<MovieResponse, BaseViewHolder<MovieResponse>>(DiffUtilCallBack) {

    object DiffUtilCallBack : DiffUtil.ItemCallback<MovieResponse>() {
        override fun areItemsTheSame(oldItem: MovieResponse, newItem: MovieResponse): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MovieResponse, newItem: MovieResponse): Boolean {
            return oldItem == newItem
        }
    }

    var listFavorite: MutableList<Favorite> = mutableListOf()
        set(value) {
            field = value
            notifyItemRangeChanged(0, value.size)
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<MovieResponse> {
        val binding = DataBindingUtil.inflate<ItemSearchBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_search,
            parent,
            false
        )

        return SearchViewHolder(
            binding = binding,
            actionMoveDetail = actionMoveDetail,
            actionFavorite = actionFavorite,
            listFavorite = listFavorite
        )
    }

    override fun onBindViewHolder(holder: BaseViewHolder<MovieResponse>, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

}