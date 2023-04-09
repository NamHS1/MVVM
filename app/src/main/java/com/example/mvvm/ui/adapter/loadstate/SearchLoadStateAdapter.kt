package com.example.mvvm.ui.adapter.loadstate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.LoadState
import com.example.mvvm.R
import com.example.mvvm.databinding.ItemSearchLoadStateBinding
import com.example.mvvm.ui.adapter.viewholder.SearchLoadStateViewHolder
import com.example.mvvm.ui.base.BaseLoadStateAdapter
import com.example.mvvm.ui.base.BaseViewHolder

class SearchLoadStateAdapter(
    private val actionRetry: () -> Unit
) : BaseLoadStateAdapter() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): BaseViewHolder<LoadState> {
        val binding = DataBindingUtil.inflate<ItemSearchLoadStateBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_search_load_state,
            parent,
            false
        )
        return SearchLoadStateViewHolder(binding, actionRetry)
    }

}