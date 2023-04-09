package com.example.mvvm.ui.adapter.viewholder

import androidx.core.view.isVisible
import androidx.paging.LoadState
import com.example.mvvm.R
import com.example.mvvm.databinding.ItemLoadingStateBinding
import com.example.mvvm.ui.base.BaseViewHolder

class HomeLoadStateViewHolder(
    private val binding: ItemLoadingStateBinding,
    private val actionRetry: () -> Unit
) : BaseViewHolder<LoadState>(binding.root) {
    override fun bind(t: LoadState?) {
        binding.container.apply {
            val margin = context.resources.getDimensionPixelSize(R.dimen.margin)
            val width =  ((context.resources.displayMetrics.widthPixels - margin * 3) / 2.5).toInt()
            layoutParams.width = width
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