package com.example.mvvm.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.mvvm.R
import com.example.mvvm.data.enumtype.ItemMovieType
import com.example.mvvm.databinding.ItemBigMovieBinding
import com.example.mvvm.databinding.ItemSmallMovieBinding
import com.example.mvvm.ui.base.BaseViewHolder
import com.example.mvvm.ui.adapter.viewholder.BigMovieViewHolder
import com.example.mvvm.ui.adapter.viewholder.SmallMovieViewHolder
import com.example.mvvm.ui.model.MovieResponse

class MovieAdapter(
    private val type: ItemMovieType,
    private val actionMoveDetail: (Int) -> Unit
) : PagingDataAdapter<MovieResponse, BaseViewHolder<MovieResponse>>(DiffUtilCallBack) {

    object DiffUtilCallBack : DiffUtil.ItemCallback<MovieResponse>() {
        override fun areItemsTheSame(oldItem: MovieResponse, newItem: MovieResponse): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MovieResponse, newItem: MovieResponse): Boolean {
            return oldItem == newItem
        }
    }
    override fun onBindViewHolder(holder: BaseViewHolder<MovieResponse>, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<MovieResponse> {
        return when (type) {
            ItemMovieType.SMALL -> {
                val binding = DataBindingUtil.inflate<ItemSmallMovieBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.item_small_movie,
                    parent,
                    false
                )
                SmallMovieViewHolder(
                    binding = binding,
                    actionMoveDetail = actionMoveDetail
                )
            }

            else -> {
                val binding = DataBindingUtil.inflate<ItemBigMovieBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.item_big_movie,
                    parent,
                    false
                )
                BigMovieViewHolder(
                    binding = binding,
                    actionMoveDetail = actionMoveDetail
                )
            }
        }
    }
}