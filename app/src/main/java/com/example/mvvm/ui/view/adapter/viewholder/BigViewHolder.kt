package com.example.mvvm.ui.view.adapter.viewholder

import android.content.Context
import android.view.View
import com.bumptech.glide.Glide
import com.example.mvvm.data.model.MovieItem
import com.example.mvvm.databinding.ItemBigMovieBinding
import com.example.mvvm.ui.base.BaseViewHolder

class BigViewHolder (
    private val context: Context,
    private val binding: ItemBigMovieBinding,
    private val onClickListener: View.OnClickListener
) : BaseViewHolder<MovieItem>(binding.root) {

    override fun bind(model: MovieItem) {
        binding.title.text = model.title

        Glide.with(context)
            .load(model.imagePath)
            .into(binding.image)

//        val param: ConstraintLayout.LayoutParams = binding.image.layoutParams as ConstraintLayout.LayoutParams
//        param.width = 0
//        binding.image.layoutParams = param
        binding.root.setOnClickListener {
            onClickListener.onClick(it)
        }
    }
}