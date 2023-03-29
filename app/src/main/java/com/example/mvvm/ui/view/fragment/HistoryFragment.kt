package com.example.mvvm.ui.view.fragment

import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
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

    override fun observeViewModel() {
        viewModel.liveData.observe(viewLifecycleOwner) {
            binding.apply {
                if (it == null || it.isEmpty()) {
                    (container as ViewGroup).onVisibility(noHistory, titleHeader)
                } else {
                    (container as ViewGroup).onVisibility(clearHistory, listHistory, titleHeader)
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
        binding.clearHistory.setOnClickListener {
            viewModel.clearHistory()
        }
    }
}