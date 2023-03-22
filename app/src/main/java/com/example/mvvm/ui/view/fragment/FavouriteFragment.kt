package com.example.mvvm.ui.view.fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mvvm.R
import com.example.mvvm.ui.base.BaseFragment
import com.example.mvvm.databinding.FragmentFavouriteBinding
import com.example.mvvm.ui.viewmodel.FavouriteViewModel

class FavouriteFragment(

    override var layoutResId: Int = R.layout.fragment_favourite,

    override var classTypeOfViewModel: Class<FavouriteViewModel> = FavouriteViewModel::class.java,

    override var viewModelFactory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(classTypeOfViewModel)) {
                @Suppress("UNCHECKED_CAST")
                return FavouriteViewModel() as T
            }
            throw IllegalArgumentException("Unable construct viewModel")
        }
    }

) : BaseFragment<FragmentFavouriteBinding, FavouriteViewModel>() {

    override fun observeViewModel() {
    }

    override fun initControls() {
    }

    override fun initEvent() {
    }
}