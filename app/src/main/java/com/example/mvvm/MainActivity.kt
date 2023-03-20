package com.example.mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mvvm.base.BaseActivity
import com.example.mvvm.databinding.ActivityMainBinding
import com.example.mvvm.viewmodel.activity.MainViewModel

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    override var layoutResId: Int = R.layout.activity_main

    override var classTypeOfViewModel: Class<MainViewModel> = MainViewModel::class.java

    override var viewModelFactory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(classTypeOfViewModel)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel() as T
            }
            throw IllegalArgumentException("Unable construct viewModel")
        }
    }

    override fun intData() {
        viewModel.liveData.observe(this) {
            it?.let {
                binding.text.text = it.stringTest
            }
        }
    }
}