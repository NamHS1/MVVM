package com.example.mvvm.ui.view.fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mvvm.R
import com.example.mvvm.databinding.FragmentMovieDetailBinding
import com.example.mvvm.ui.base.BaseFragment
import com.example.mvvm.ui.viewmodel.MovieDetailViewModel

class MovieDetailFragment(

    override var layoutResId: Int = R.layout.fragment_movie_detail,
    override var classTypeOfViewModel: Class<MovieDetailViewModel> = MovieDetailViewModel::class.java,
    override var viewModelFactory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(classTypeOfViewModel)) {
                @Suppress("UNCHECKED_CAST")
                return MovieDetailViewModel() as T
            }
            throw IllegalArgumentException("Unable construct viewModel")
        }
    }

) : BaseFragment<FragmentMovieDetailBinding, MovieDetailViewModel>() {
    override fun observeViewModel() {
    }

    override fun initControls() {
    }

    override fun initEvent() {
    }
}