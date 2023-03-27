package com.example.mvvm.ui.view.fragment

import android.view.View
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mvvm.R
import com.example.mvvm.data.enum.State
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

    override fun fetchData() {
        viewModel.checkFavorite(viewModel.id)
    }

    override fun observeViewModel() {
        viewModel.liveData.observe(viewLifecycleOwner) {
            it?.let {
                binding.apply {
                    txt.text = it.toString()
                }
                viewModel.addHistory(it)
            }

        }
        viewModel.stateMovieDetail.observe(viewLifecycleOwner) {
            it?.let {
                when (it) {
                    State.LOADING -> binding.loading.visibility = View.VISIBLE
                    else -> binding.loading.visibility = View.GONE
                }
            }

        }

        viewModel.favourite.observe(viewLifecycleOwner) {
            binding.favourite.isChecked = it
        }
    }

    override fun initControls() {
    }

    override fun initEvent() {
        binding.favourite.setOnClickListener {
            viewModel.handleFavorite((it as AppCompatCheckBox).isChecked)
        }
    }
}