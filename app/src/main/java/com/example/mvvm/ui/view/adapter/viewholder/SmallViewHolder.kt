package com.example.mvvm.ui.view.adapter.viewholder

import android.content.Context
import android.view.View.OnClickListener
import com.bumptech.glide.Glide
import com.example.mvvm.data.model.MovieItem
import com.example.mvvm.databinding.ItemSmallMovieBinding
import com.example.mvvm.ui.base.BaseViewHolder

class SmallViewHolder(
    private val context: Context,
    private val binding: ItemSmallMovieBinding,
    private val onClickListener: OnClickListener
) : BaseViewHolder<MovieItem>(binding.root) {

    override fun bind(model: MovieItem) {
        binding.title.text = model.title

        Glide.with(context)
            .load(model.imagePath)
            .into(binding.image)

        binding.root.setOnClickListener {
            onClickListener.onClick(it)
        }
    }
}