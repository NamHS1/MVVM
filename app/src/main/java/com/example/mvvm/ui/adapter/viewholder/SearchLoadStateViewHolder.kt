package com.example.mvvm.ui.adapter.viewholder

import androidx.core.view.isVisible
import androidx.paging.LoadState
import com.example.mvvm.databinding.ItemSearchLoadStateBinding
import com.example.mvvm.ui.base.BaseViewHolder

class SearchLoadStateViewHolder(
    private val binding: ItemSearchLoadStateBinding,
    private val actionRetry: () -> Unit
) : BaseViewHolder<LoadState>(binding.root) {
    override fun bind(t: LoadState?) {
        binding.container.apply {
            val width =  context.resources.displayMetrics.widthPixels
            layoutParams.width = width
            layoutParams.height = width / 4
        }
        when (t) {
            is LoadState.Error -> {
                binding.loading.isVisible = false
                binding.reload.isVisible = true
                binding.root.setOnClickListener {
                    actionRetry.invoke()
                }
            }
            is LoadState.Loading -> {
                binding.loading.isVisible = true
                binding.reload.isVisible = false
            }
            else -> {
                binding.loading.isVisible = false
                binding.reload.isVisible = false
            }
        }
    }
}