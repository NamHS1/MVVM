package com.example.mvvm.ui.adapter.loadstate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.LoadState
import com.example.mvvm.R
import com.example.mvvm.databinding.ItemLoadingStateBinding
import com.example.mvvm.ui.adapter.viewholder.HomeLoadStateViewHolder
import com.example.mvvm.ui.base.BaseLoadStateAdapter
import com.example.mvvm.ui.base.BaseViewHolder

class HomeLoadStateAdapter(
    private val actionRetry: () -> Unit
) : BaseLoadStateAdapter() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): BaseViewHolder<LoadState> {
        val binding = DataBindingUtil.inflate<ItemLoadingStateBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_loading_state,
            parent,
            false
        )
        return HomeLoadStateViewHolder(binding, actionRetry)
    }
}