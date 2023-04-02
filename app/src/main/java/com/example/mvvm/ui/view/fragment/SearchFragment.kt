package com.example.mvvm.ui.view.fragment

import android.view.View
import android.view.View.OnFocusChangeListener
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.example.mvvm.R
import com.example.mvvm.databinding.FragmentSearchBinding
import com.example.mvvm.extension.hideKeyBoard
import com.example.mvvm.extension.showKeyBoard
import com.example.mvvm.ui.base.BaseFragment
import com.example.mvvm.ui.viewmodel.SearchViewModel

class SearchFragment(
    override var layoutResId: Int = R.layout.fragment_search,
    override var classTypeOfViewModel: Class<SearchViewModel> = SearchViewModel::class.java
) : BaseFragment<FragmentSearchBinding, SearchViewModel>() {

    private val controller by lazy {
        findNavController()
    }

    override fun observeViewModel() {
    }

    override fun initControls() {
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
                    binding.clearTextSearch.visibility = View.GONE
                } else {
                    binding.clearTextSearch.visibility = View.VISIBLE
                }
            }
            clearTextSearch.setOnClickListener {
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