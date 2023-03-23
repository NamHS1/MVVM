package com.example.mvvm.ui.view.adapter.viewholder

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.mvvm.data.enum.State
import com.example.mvvm.data.model.MovieItem
import com.example.mvvm.databinding.ItemBigMovieBinding
import com.example.mvvm.extension.onVisibility
import com.example.mvvm.ui.base.BaseViewHolder

class BigViewHolder(
    private val context: Context,
    private val binding: ItemBigMovieBinding,
    private val actionMoveDetail: View.OnClickListener,
    private val actionReload: View.OnClickListener,
) : BaseViewHolder<MovieItem>(binding.root) {

    override fun bind(model: MovieItem?, state: State) {
        binding.apply {

            when (state) {
                State.LOADING -> {
                    (container as ViewGroup).onVisibility(loading)
                }
                State.ERROR -> {
                    (container as ViewGroup).onVisibility(loadMore)

                    binding.root.setOnClickListener { it1 ->
                        actionReload.onClick(it1)
                    }
                }
                else -> {
                    (container as ViewGroup).onVisibility(title, image)

                    model?.let {
                        binding.title.text = it.title

                        Glide.with(context)
                            .load(it.imagePath)
                            .into(binding.image)

                        binding.root.setOnClickListener { it1 ->
                            actionMoveDetail.onClick(it1)
                        }
                    }
                }
            }
        }
    }
}