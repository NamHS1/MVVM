package com.example.mvvm.base

import android.content.Context
import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

abstract class BaseFragment<DB : ViewDataBinding, VM : BaseViewModel> : Fragment() {

    private lateinit var binding: DB

    private lateinit var viewModel: VM

    protected abstract val layoutResId: Int

    protected abstract val classTypeOfViewModel: Class<VM>

    protected abstract val viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)[classTypeOfViewModel]
        binding.lifecycleOwner = this
    }
}