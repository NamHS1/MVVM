package com.example.mvvm.ui.view

import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.mvvm.R
import com.example.mvvm.ui.base.BaseActivity
import com.example.mvvm.databinding.ActivityMainBinding
import com.example.mvvm.ui.viewmodel.MainViewModel

class MainActivity(
    override var layoutResId: Int = R.layout.activity_main,

    override var classTypeOfViewModel: Class<MainViewModel> = MainViewModel::class.java

) : BaseActivity<ActivityMainBinding, MainViewModel>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment
        val navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)
    }

    override fun observeViewModel() {}

    override fun initControls() {}

    override fun initEvent() {
    }

}