package com.example.mvvm.ui.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm.data.enum.State

abstract class BaseViewHolder<in T>(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun bind(model: T?, state: State)
}