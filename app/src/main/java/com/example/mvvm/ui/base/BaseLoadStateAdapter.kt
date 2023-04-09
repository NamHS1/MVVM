package com.example.mvvm.ui.base

import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter

abstract class BaseLoadStateAdapter : LoadStateAdapter<BaseViewHolder<LoadState>>() {

    override fun onBindViewHolder(holder: BaseViewHolder<LoadState>, loadState: LoadState) {
        holder.bind(loadState)
    }
}