package com.example.mvvm.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider

abstract class BaseActivity<DB : ViewDataBinding, VM : BaseViewModel> : AppCompatActivity() {

    protected lateinit var binding: DB

    private lateinit var viewModel: VM

    protected abstract var layoutResId: Int

    protected abstract var classTypeOfViewModel: Class<VM>

    protected abstract var viewModelFactory: ViewModelProvider.Factory

    protected abstract fun observeViewModel(viewModel: VM)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutResId)
        binding.lifecycleOwner = this

        viewModel = ViewModelProvider(this, viewModelFactory)[classTypeOfViewModel]
        viewModel.initViewModel()

        observeViewModel(viewModel)
    }
}