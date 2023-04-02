package com.example.mvvm.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

abstract class BaseFragment<DB : ViewDataBinding, VM : BaseViewModel> : Fragment() {

    protected lateinit var binding: DB

    protected lateinit var viewModel: VM

    protected abstract var layoutResId: Int

    protected abstract var classTypeOfViewModel: Class<VM>

    protected open fun initHeader() {}

    protected abstract fun initControls()

    protected abstract fun initEvent()

    protected abstract fun observeViewModel()

    protected open fun fetchData() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[classTypeOfViewModel]

        arguments?.let {
            viewModel.getArguments(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutResId, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initHeader()
        initControls()
        initEvent()

        observeViewModel()
        fetchData()
    }
}