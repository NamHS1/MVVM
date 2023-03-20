package com.example.mvvm.ui.view

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.mvvm.R
import com.example.mvvm.ui.base.BaseActivity
import com.example.mvvm.databinding.ActivityMainBinding
import com.example.mvvm.ui.viewmodel.MainViewModel

class MainActivity(
    override var layoutResId: Int = R.layout.activity_main,

    override var classTypeOfViewModel: Class<MainViewModel> = MainViewModel::class.java,

    override var viewModelFactory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(classTypeOfViewModel)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel() as T
            }
            throw IllegalArgumentException("Unable construct viewModel")
        }
    }
) : BaseActivity<ActivityMainBinding, MainViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment
        val navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)
    }

    override fun observeViewModel(viewModel: MainViewModel) {}

}