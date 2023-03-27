package com.example.mvvm.ui.view.fragment

import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvm.R
import com.example.mvvm.ui.base.BaseFragment
import com.example.mvvm.databinding.FragmentFavouriteBinding
import com.example.mvvm.extension.onVisibility
import com.example.mvvm.ui.view.adapter.FavoriteAdapter
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

    private val favoriteAdapter: FavoriteAdapter by lazy {
        FavoriteAdapter(
            requireContext(),
            actionMoveDetail = {
                findNavController().navigate(FavouriteFragmentDirections.actionFavouriteToMovieDetail(id = it))
            },
            actionFavorite = { position, id ->
                viewModel.removeFavorite(
                    id = id,
                    position = position,
                    adapter = favoriteAdapter
                )
            }
        )
    }

    override fun fetchData() {
        viewModel.fetchData()
    }

    override fun observeViewModel() {
        viewModel.liveData.observe(viewLifecycleOwner) {
            binding.apply {
                if (it == null || it.isEmpty()) {
                    (container as ViewGroup).onVisibility(noFavorite)
                } else {
                    (container as ViewGroup).onVisibility(listFavorite)
                }
                favoriteAdapter.movies = it.toMutableList()
            }
        }
    }

    override fun initControls() {
        binding.listFavorite.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = favoriteAdapter

            setHasFixedSize(true)
        }
    }

    override fun initEvent() {
    }
}