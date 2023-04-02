package com.example.mvvm.ui.view.fragment

import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvm.MovieApplication
import com.example.mvvm.R
import com.example.mvvm.databinding.FragmentHistoryBinding
import com.example.mvvm.extension.onVisibility
import com.example.mvvm.ui.base.BaseFragment
import com.example.mvvm.ui.view.adapter.HistoryAdapter
import com.example.mvvm.ui.viewmodel.HistoryViewModel

class HistoryFragment(

    override var layoutResId: Int = R.layout.fragment_history,

    override var classTypeOfViewModel: Class<HistoryViewModel> = HistoryViewModel::class.java

) : BaseFragment<FragmentHistoryBinding, HistoryViewModel>() {

    private val historyAdapter: HistoryAdapter by lazy {
        HistoryAdapter(
            requireContext(),
            actionMoveDetail = {
                controller.navigate(HistoryFragmentDirections.actionHistoryToMovieDetail(id = it))
            })
    }

    private val controller by lazy {
        findNavController()
    }

    override fun initHeader() {
        binding.header.apply {
            titleHeader.text = getString(R.string.history)
            actionHeader.text = getString(R.string.clear_all)
            actionHeader.setCompoundDrawablesWithIntrinsicBounds(
                null,
                null,
                ContextCompat.getDrawable(
                    MovieApplication.application(),
                    R.drawable.icon_bookmark_remove
                ),
                null
            )
        }
    }

    override fun observeViewModel() {
        viewModel.liveData.observe(viewLifecycleOwner) {
            binding.apply {
                if (it == null || it.isEmpty()) {
                    (container as ViewGroup).onVisibility(noHistory, header.root)
                    (header.root as ViewGroup).onVisibility(header.titleHeader)
                } else {
                    (container as ViewGroup).onVisibility(header.root, listHistory)
                    (header.root as ViewGroup).onVisibility(header.titleHeader, header.actionHeader)
                }
                historyAdapter.movies = it.toMutableList()
            }
        }
    }

    override fun fetchData() {
        super.fetchData()
        viewModel.fetchData()
    }

    override fun initControls() {
        binding.listHistory.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = historyAdapter

            setHasFixedSize(true)
        }
    }

    override fun initEvent() {
        binding.header.actionHeader.setOnClickListener {
            viewModel.clearHistory()
        }
    }
}