package com.example.mvvm.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider

abstract class BaseActivity<DB : ViewDataBinding, VM : BaseViewModel> : AppCompatActivity() {

    lateinit var binding: DB

    lateinit var viewModel: VM

    protected abstract var layoutResId: Int

    protected abstract var classTypeOfViewModel: Class<VM>

    protected abstract var viewModelFactory: ViewModelProvider.Factory

    protected abstract fun onIntData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)[classTypeOfViewModel]

        binding = DataBindingUtil.setContentView(this, layoutResId)
        binding.lifecycleOwner = this

        viewModel.attachData()

        intData()
    }
}