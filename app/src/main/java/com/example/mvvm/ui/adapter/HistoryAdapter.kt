package com.example.mvvm.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm.R
import com.example.mvvm.data.model.MovieDetail
import com.example.mvvm.databinding.ItemHistoryBinding
import com.example.mvvm.ui.base.BaseViewHolder
import com.example.mvvm.ui.adapter.viewholder.HistoryViewHolder

class HistoryAdapter(
    context: Context,
    private val actionMoveDetail: (Int) -> Unit
) : RecyclerView.Adapter<BaseViewHolder<MovieDetail>>() {

    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    var movies: MutableList<MovieDetail> = mutableListOf()
        set(value) {
            field = value
            notifyItemRangeInserted(0, field.size)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<MovieDetail> {
        val binding = DataBindingUtil.inflate<ItemHistoryBinding>(
            layoutInflater,
            R.layout.item_history,
            parent,
            false
        )
        return HistoryViewHolder(binding, actionMoveDetail)
    }

    override fun getItemCount() = movies.size

    override fun onBindViewHolder(holder: BaseViewHolder<MovieDetail>, position: Int) {
        holder.bind(movies[position])
    }
}