package com.example.mvvm.ui.view.fragment

import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.mvvm.R
import com.example.mvvm.data.enum.ItemMovieType
import com.example.mvvm.data.model.MovieItem
import com.example.mvvm.databinding.FragmentHomeBinding
import com.example.mvvm.ui.base.BaseFragment
import com.example.mvvm.ui.view.adapter.MovieAdapter
import com.example.mvvm.ui.viewmodel.HomeViewModel
import com.example.mvvm.util.EventObserver

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
            actionMoveDetail = {
                controller.navigate(HomeFragmentDirections.actionHomeToMovieDetail())
            },
            actionLoadMore = {
                viewModel.getMoviesPopular()
            },
            actionReload = {
                viewModel.getMoviesPopular()
            }
        )
    }
    private val nowPlayingAdapter by lazy {
        MovieAdapter(
            requireContext(),
            ItemMovieType.SMALL,
            actionMoveDetail = {
                controller.navigate(HomeFragmentDirections.actionHomeToMovieDetail())
            },
            actionLoadMore = {
                viewModel.getMoviesNowPlaying()
            },
            actionReload = {
                viewModel.getMoviesNowPlaying()
            }
        )
    }
    private val upComingAdapter by lazy {
        MovieAdapter(
            requireContext(),
            ItemMovieType.BIG,
            actionMoveDetail = {
                controller.navigate(HomeFragmentDirections.actionHomeToMovieDetail())
            },
            actionLoadMore = {},
            actionReload = {
                viewModel.getMoviesUpComing()
            }
        )
    }
    private val controller by lazy {
        findNavController()
    }

    override fun observeViewModel() {
        viewModel.moviesNowPlaying.observe(viewLifecycleOwner, EventObserver {
            nowPlayingAdapter.movies = it.movies?.toMutableList() as MutableList<MovieItem>
        })
        viewModel.moviesPopular.observe(viewLifecycleOwner, EventObserver {
            popularAdapter.movies = it.movies?.toMutableList() as MutableList<MovieItem>
        })
        viewModel.moviesUpComing.observe(viewLifecycleOwner, EventObserver {
            it.movies?.let { movie ->
                initIndicatorBanner(movie.size)
                upComingAdapter.movies = movie.toMutableList()
            }
        })

        viewModel.statePopular.observe(viewLifecycleOwner, EventObserver {
            popularAdapter.state = it
        })
        viewModel.stateUpComing.observe(viewLifecycleOwner, EventObserver {
            upComingAdapter.state = it
        })
        viewModel.stateNowPlaying.observe(viewLifecycleOwner, EventObserver {
            nowPlayingAdapter.state = it
        })
    }

    override fun initControls() {
        binding.banner.apply {
            adapter = upComingAdapter
            setPageTransformer(this)
            initIndicatorBanner(upComingAdapter.itemCount)
        }

        intiRecyclerView(binding.listPopular, popularAdapter)
        intiRecyclerView(binding.listPlayNow, nowPlayingAdapter)
    }

    override fun initEvent() {
        binding.banner.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                (binding.indicatorBanner[position] as RadioButton).isChecked = true
            }
        })
    }

    private fun setPageTransformer(viewPager2: ViewPager2) {
        viewPager2.apply {
            clipToPadding = false
            clipChildren = false
            offscreenPageLimit = 1

            val maxAlpha = 1.0f
            val minAlpha = 0.8f

            val maxScale = 1f
            val scalePercent = 0.9f
            val minScale = scalePercent * maxScale

            val screenHeight = resources.displayMetrics.heightPixels
            val nextItemTranslationX = 2.5F * screenHeight / 60
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

    private fun initIndicatorBanner(size: Int) {
        binding.indicatorBanner.removeAllViews()
        for (index in 0 until size) {
            val radioButton = RadioButton(context)
            radioButton.apply {
                val params = RadioGroup.LayoutParams(
                    RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT
                )
                val margin = context.resources.getDimensionPixelSize(R.dimen.margin_indicator)
                params.setMargins(margin, margin, margin, 0)

                layoutParams = params
                buttonDrawable = ContextCompat.getDrawable(context, R.drawable.drawable_indicator)
            }

            binding.indicatorBanner.addView(radioButton)
        }
        (binding.indicatorBanner[0] as RadioButton).isChecked = true
    }

}