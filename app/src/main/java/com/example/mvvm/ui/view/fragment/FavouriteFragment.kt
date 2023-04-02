package com.example.mvvm.ui.view.fragment

import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
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

    override var classTypeOfViewModel: Class<FavouriteViewModel> = FavouriteViewModel::class.java

) : BaseFragment<FragmentFavouriteBinding, FavouriteViewModel>() {

    private val controller: NavController by lazy {
        findNavController()
    }

    private val favoriteAdapter: FavoriteAdapter by lazy {
        FavoriteAdapter(
            requireContext(),
            actionMoveDetail = {
                controller.navigate(FavouriteFragmentDirections.actionFavouriteToMovieDetail(id = it))
            },
            actionFavorite = { position, favorite ->
                favoriteAdapter.favorites.removeAt(position)
                favoriteAdapter.notifyItemRemoved(position)
                favoriteAdapter.notifyItemRangeChanged(position, favoriteAdapter.favorites.size)

                viewModel.deleteFavorite(favorite)
            }
        )
    }

    override fun initHeader() {
        binding.header.apply {
            actionHeader.visibility = View.GONE
            titleHeader.text = getString(R.string.favorite)
        }
    }

    override fun observeViewModel() {
        viewModel.favorites().observe(viewLifecycleOwner) {
            binding.apply {
                if (it == null || it.isEmpty()) {
                    (container as ViewGroup).onVisibility(noFavorite, header.root)
                } else {
                    (container as ViewGroup).onVisibility(listFavorite, header.root)
                }
                favoriteAdapter.favorites = it.reversed().toMutableList()
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