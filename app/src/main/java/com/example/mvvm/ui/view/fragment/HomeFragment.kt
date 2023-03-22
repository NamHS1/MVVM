package com.example.mvvm.ui.view.fragment

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.mvvm.R
import com.example.mvvm.data.enum.ItemMovieType
import com.example.mvvm.ui.base.BaseFragment
import com.example.mvvm.databinding.FragmentHomeBinding
import com.example.mvvm.ui.view.adapter.MovieAdapter
import com.example.mvvm.ui.viewmodel.HomeViewModel

class HomeFragment(

    override var layoutResId: Int = R.layout.fragment_home,

    override var classTypeOfViewModel: Class<HomeViewModel> = HomeViewModel::class.java,

    override var viewModelFactory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(classTypeOfViewModel)) {
                @Suppress("UNCHECKED_CAST")
                return HomeViewModel() as T
            }
            throw IllegalArgumentException("Unable construct viewModel")
        }
    }

) : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    private val popularAdapter by lazy {
        MovieAdapter(
            requireContext(),
            ItemMovieType.SMALL,
            onClickListener
        )
    }
    private val nowPlayingMapper by lazy {
        MovieAdapter(
            requireContext(),
            ItemMovieType.SMALL,
            onClickListener
        )
    }
    private val upComingAdapter by lazy {
        MovieAdapter(
            requireContext(),
            ItemMovieType.BIG,
            onClickListener
        )
    }

    private val onClickListener = View.OnClickListener {}

    override fun observeViewModel() {
        viewModel.moviesNowPlaying.observe(viewLifecycleOwner) {
            nowPlayingMapper.movies = it?.movies.orEmpty()
        }
        viewModel.moviesPopular.observe(viewLifecycleOwner) {
            popularAdapter.movies = it?.movies.orEmpty()

        }
        viewModel.moviesUpComing.observe(viewLifecycleOwner) {
            upComingAdapter.movies = it?.movies.orEmpty()
        }
    }

    override fun initControls() {
        binding.banner.apply {
            adapter = upComingAdapter
            setPageTransformer(this)
        }

        intiRecyclerView(binding.listPopular, popularAdapter)
        intiRecyclerView(binding.listPlayNow, nowPlayingMapper)
    }

    private fun setPageTransformer(viewPager2: ViewPager2) {
        viewPager2.apply {
            clipToPadding = false
            clipChildren = false
            offscreenPageLimit = 1

            val maxAlpha = 1.0f
            val minAlpha = 0f

            val maxScale = 1f
            val scalePercent = 0.5f
            val minScale = scalePercent * maxScale

            val screenHeight = resources.displayMetrics.heightPixels
            val nextItemTranslationX = 19f * screenHeight / 60
            setPageTransformer { view, position ->
                // position  -1: left, 0: center, 1: right
                val absPosition = kotlin.math.abs(position)
                // alpha from MIN_ALPHA to MAX_ALPHA
                view.alpha = maxAlpha - (maxAlpha - minAlpha) * absPosition
                // scale from MIN_SCALE to MAX_SCALE
                val scale = maxScale - (maxScale - minScale) * absPosition
                view.scaleY = scale
                view.scaleX = scale
                // translation X
                view.translationX = -position * nextItemTranslationX
            }
        }

    }

    private fun intiRecyclerView(recyclerView: RecyclerView, movieAdapter: MovieAdapter) {
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = movieAdapter
        }
    }

    override fun initEvent() {
    }

}