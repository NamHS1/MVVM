package com.example.mvvm.ui.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm.R
import com.example.mvvm.data.enum.ItemMovieType
import com.example.mvvm.data.enum.State
import com.example.mvvm.data.model.MovieItem
import com.example.mvvm.databinding.ItemBigMovieBinding
import com.example.mvvm.databinding.ItemSmallMovieBinding
import com.example.mvvm.ui.base.BaseViewHolder
import com.example.mvvm.ui.view.adapter.viewholder.BigViewHolder
import com.example.mvvm.ui.view.adapter.viewholder.SmallViewHolder

class MovieAdapter(
    private val context: Context,
    private val type: ItemMovieType,
    private val actionMoveDetail: OnClickListener,
    private val actionLoadMore: OnClickListener,
    private val actionReload: OnClickListener,
) : RecyclerView.Adapter<BaseViewHolder<MovieItem>>() {

    var movies: MutableList<MovieItem> = mutableListOf()
        set(value) {
            if (movies.isNotEmpty()) {
                field.addAll(value)
            } else {
                field = value
            }
            notifyItemRangeInserted(movies.size, field.size)
        }
    var state: State = State.LOADING
        set(value) {
            field = value
            notifyItemChanged(movies.size)
        }

    override fun onBindViewHolder(holder: BaseViewHolder<MovieItem>, position: Int) {
        if (position < itemCount - 1) {
            holder.bind(movies[position], State.SUCCESS)
        } else {
            holder.bind(null, state)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<MovieItem> {
        return when (type) {
            ItemMovieType.SMALL -> {
                val binding = DataBindingUtil.inflate<ItemSmallMovieBinding>(
                    LayoutInflater.from(context),
                    R.layout.item_small_movie,
                    parent,
                    false
                )
                SmallViewHolder(
                    context = context,
                    binding = binding,
                    actionReload = actionReload,
                    actionLoadMore = actionLoadMore,
                    actionMoveDetail = actionMoveDetail
                )
            }

            else -> {
                val binding = DataBindingUtil.inflate<ItemBigMovieBinding>(
                    LayoutInflater.from(context),
                    R.layout.item_big_movie,
                    parent,
                    false
                )
                BigViewHolder(
                    context = context,
                    binding = binding,
                    actionReload = actionReload,
                    actionMoveDetail = actionMoveDetail
                )
            }
        }
    }

    override fun getItemCount() = when (state) {
        State.LOAD_MORE -> {
            movies.size + 1
        }
        State.LOADING, State.ERROR -> {
            if (movies.isNotEmpty()) {
                movies.size + 1
            } else {
                1
            }
        }
        else -> {
            movies.size
        }
    }
}