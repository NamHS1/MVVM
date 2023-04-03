package com.example.mvvm.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm.R
import com.example.mvvm.data.database.entity.Favorite
import com.example.mvvm.data.model.SearchItem
import com.example.mvvm.databinding.ItemSearchBinding
import com.example.mvvm.ui.adapter.viewholder.SearchViewHolder
import com.example.mvvm.ui.base.BaseViewHolder

class SearchAdapter(
    context: Context,
    private val actionMoveDetail: (Int) -> Unit,
    private val actionFavorite: (Favorite, Boolean) -> Unit
) : RecyclerView.Adapter<BaseViewHolder<SearchItem>>() {

    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    var listSearch: MutableList<SearchItem> = mutableListOf()
        set(value) {
            field = value
            notifyItemRangeChanged(0, value.size)
        }

    var listFavorite: MutableList<Favorite> = mutableListOf()
        set(value) {
            field = value
            notifyItemRangeChanged(0, value.size)
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<SearchItem> {
        val binding = DataBindingUtil.inflate<ItemSearchBinding>(
            layoutInflater,
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

    override fun getItemCount(): Int = listSearch.size

    override fun onBindViewHolder(holder: BaseViewHolder<SearchItem>, position: Int) {
        holder.bind(listSearch[position])
    }

}