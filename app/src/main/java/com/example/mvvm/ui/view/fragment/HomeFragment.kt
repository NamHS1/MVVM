package com.example.mvvm.ui.view.fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mvvm.R
import com.example.mvvm.ui.base.BaseFragment
import com.example.mvvm.databinding.FragmentHomeBinding
import com.example.mvvm.ui.viewmodel.HomeViewModel

class HomeFragment(

    override var layoutResId: Int = R.layout.fragment_home,

    override var classTypeOfViewModel: Class<HomeViewModel> = HomeViewModel::class.java,

    override var viewModelFactory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(classTypeOfViewModel)) {
                @Suppress("UNCHECKED_CAST")
                return HomeViewModel() as T
            }
            throw IllegalArgumentException("Unable construct viewModel")
        }
    }
) : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    override fun observeViewModel(viewModel: HomeViewModel) {
        viewModel.moviesNowPlaying.observe(viewLifecycleOwner) {
        }
        viewModel.moviesPopular.observe(viewLifecycleOwner) {
        }
        viewModel.moviesUpComing.observe(viewLifecycleOwner) {
        }
    }

}