package com.example.mvvm.ui.view.adapter.viewholder

import android.content.Context
import android.view.View.*
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.mvvm.R
import com.example.mvvm.data.enum.State
import com.example.mvvm.data.model.MovieItem
import com.example.mvvm.databinding.ItemSmallMovieBinding
import com.example.mvvm.extension.onVisibility
import com.example.mvvm.ui.base.BaseViewHolder

class SmallViewHolder(
    private val context: Context,
    private val binding: ItemSmallMovieBinding,
    private val actionMoveDetail: OnClickListener,
    private val actionReload: OnClickListener,
    private val actionLoadMore: OnClickListener
) : BaseViewHolder<MovieItem>(binding.root) {

    override fun bind(model: MovieItem?, state: State) {
        binding.apply {
            image.apply {
                val margin = context.resources.getDimensionPixelSize(R.dimen.margin)
                binding.image.layoutParams.width =
                    ((context.resources.displayMetrics.widthPixels - margin * 3) / 2.5).toInt()
            }

            when (state) {
                State.LOADING -> {
                    (container as ViewGroup).onVisibility(loading)
                }
                State.ERROR-> {
                    (container as ViewGroup).onVisibility(reload)

                    binding.root.setOnClickListener {
                        actionReload.onClick(it)
                    }
                }
                State.LOAD_MORE -> {
                    (container as ViewGroup).onVisibility(loadMore)

                    binding.root.setOnClickListener {
                        actionLoadMore.onClick(it)
                    }
                }
                else -> {
                    (container as ViewGroup).onVisibility(title, image)
                    model?.let { model ->
                        title.text = model.title

                        Glide.with(context)
                            .load(model.imagePath)
                            .into(binding.image)

                        binding.root.setOnClickListener {
                            actionMoveDetail.onClick(it)
                        }
                    }
                }
            }
        }
    }
}