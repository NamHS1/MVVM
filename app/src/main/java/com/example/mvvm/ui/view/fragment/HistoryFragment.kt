package com.example.mvvm.ui.view.fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mvvm.R
import com.example.mvvm.databinding.FragmentHistoryBinding
import com.example.mvvm.ui.base.BaseFragment
import com.example.mvvm.ui.viewmodel.HistoryViewModel

class HistoryFragment(
    override var layoutResId: Int = R.layout.fragment_history,
    override var classTypeOfViewModel: Class<HistoryViewModel> = HistoryViewModel::class.java,
    override var viewModelFactory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(classTypeOfViewModel)) {
                @Suppress("UNCHECKED_CAST")
                return HistoryViewModel() as T
            }
            throw IllegalArgumentException("Unable construct viewModel")
        }
    }
) : BaseFragment<FragmentHistoryBinding, HistoryViewModel>() {
    override fun observeViewModel() {
    }

    override fun initControls() {
    }

    override fun initEvent() {
    }
}