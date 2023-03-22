package com.example.mvvm.ui.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm.R
import com.example.mvvm.data.enum.ItemMovieType
import com.example.mvvm.data.model.MovieItem
import com.example.mvvm.databinding.ItemBigMovieBinding
import com.example.mvvm.databinding.ItemSmallMovieBinding
import com.example.mvvm.ui.base.BaseViewHolder
import com.example.mvvm.ui.view.adapter.viewholder.BigViewHolder
import com.example.mvvm.ui.view.adapter.viewholder.SmallViewHolder

class MovieAdapter(
    private val context: Context,
    private val type: ItemMovieType,
    private val onClickListener: OnClickListener
) : RecyclerView.Adapter<BaseViewHolder<MovieItem>>() {

    var movies: List<MovieItem> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onBindViewHolder(holder: BaseViewHolder<MovieItem>, position: Int) {
        holder.bind(movies[position])
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
                SmallViewHolder(context, binding, onClickListener)
            }

            else -> {
                val binding = DataBindingUtil.inflate<ItemBigMovieBinding>(
                    LayoutInflater.from(context),
                    R.layout.item_big_movie,
                    parent,
                    false
                )
                BigViewHolder(context, binding, onClickListener)
            }
        }
    }

    override fun getItemCount() = movies.size
}