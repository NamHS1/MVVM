package com.example.mvvm.ui.view.fragment

import android.view.View
import android.view.View.*
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvm.R
import com.example.mvvm.data.enumtype.NetworkState
import com.example.mvvm.databinding.FragmentSearchBinding
import com.example.mvvm.extension.hideKeyBoard
import com.example.mvvm.extension.showKeyBoard
import com.example.mvvm.ui.adapter.SearchAdapter
import com.example.mvvm.ui.adapter.loadstate.SearchLoadStateAdapter
import com.example.mvvm.ui.base.BaseFragment
import com.example.mvvm.ui.viewmodel.SearchViewModel
import com.example.mvvm.util.EventObserver
import kotlinx.coroutines.flow.collectLatest

class SearchFragment(
    override var layoutResId: Int = R.layout.fragment_search,
    override var classTypeOfViewModel: Class<SearchViewModel> = SearchViewModel::class.java
) : BaseFragment<FragmentSearchBinding, SearchViewModel>() {

    private val controller by lazy {
        findNavController()
    }

    private val searchAdapter: SearchAdapter by lazy {
        SearchAdapter(
            actionFavorite = { favorite, isCheck ->
                viewModel.handFavorite(favorite, isCheck)
            },
            actionMoveDetail = {
                controller.navigate(SearchFragmentDirections.actionMoveToMovieDetail(id = it))
            }
        )
    }

    override fun observeViewModel() {
        viewModel.liveData.observe(viewLifecycleOwner, EventObserver {
            searchAdapter.submitData(lifecycle, it)
            binding.root.requestFocus()
        })
        viewModel.favorites().observe(viewLifecycleOwner) {
            searchAdapter.listFavorite = it.toMutableList()
        }
        viewModel.networkState.observe(viewLifecycleOwner, EventObserver {
            when (it) {
                NetworkState.LOADING -> setState(binding.loading)
                NetworkState.ERROR -> setState(binding.iconSearch)
                else ->  setState(binding.listSearch)
            }
        })
    }

    private fun setState(view: View) {
        val array = listOf(binding.listSearch, binding.loading, binding.iconSearch)
        array.forEach {
            if (it == view) {
                it.visibility = VISIBLE
            } else {
                it.visibility = GONE
            }
        }
    }

    override fun initControls() {
        binding.listSearch.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)

            adapter = searchAdapter.withLoadStateFooter(
                footer = SearchLoadStateAdapter {
                    searchAdapter.retry()
                }
            )
        }

        if (searchAdapter.itemCount == 0) {
            binding.textSearch.requestFocus()
        }
    }

    override fun initEvent() {
        binding.apply {
            textSearch.onFocusChangeListener = OnFocusChangeListener { v, hasFocus ->
                if (!hasFocus) {
                    v.hideKeyBoard()
                } else {
                    v.showKeyBoard()
                }
            }
            textSearch.addTextChangedListener {
                if (it.isNullOrEmpty()) {
                    binding.clearTextSearch.visibility = GONE
                } else {
                    binding.clearTextSearch.visibility = VISIBLE
                }
                viewModel.changeTextSearch(keyword = it.toString())
            }
            clearTextSearch.setOnClickListener {
                binding.iconSearch.isVisible = true
                searchAdapter.submitData(lifecycle, PagingData.empty())
                textSearch.text = null
                textSearch.requestFocus()
            }
            btnBack.setOnClickListener {
                controller.popBackStack()
            }
            root.setOnClickListener {}//do nothing
        }
    }
}