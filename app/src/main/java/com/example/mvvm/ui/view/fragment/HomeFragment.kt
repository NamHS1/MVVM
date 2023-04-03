package com.example.mvvm.ui.view.fragment

import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.mvvm.MovieApplication
import com.example.mvvm.R
import com.example.mvvm.data.enumtype.ItemMovieType
import com.example.mvvm.databinding.FragmentHomeBinding
import com.example.mvvm.extension.orEmpty
import com.example.mvvm.ui.base.BaseFragment
import com.example.mvvm.ui.adapter.MovieAdapter
import com.example.mvvm.ui.viewmodel.HomeViewModel
import com.example.mvvm.util.EventObserver
import com.example.mvvm.util.PageTransformer

class HomeFragment(

    override var layoutResId: Int = R.layout.fragment_home,

    override var classTypeOfViewModel: Class<HomeViewModel> = HomeViewModel::class.java

) : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    private val popularAdapter by lazy {
        MovieAdapter(
            requireContext(),
            ItemMovieType.SMALL,
            actionMoveDetail = {
                controller.navigate(HomeFragmentDirections.actionMoveToMovieDetail(id = it))
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
                controller.navigate(HomeFragmentDirections.actionMoveToMovieDetail(id = it))
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
                controller.navigate(HomeFragmentDirections.actionMoveToMovieDetail(id = it))
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

    override fun initHeader() {
        binding.header.apply {
            titleHeader.text = getString(R.string.home)
            actionHeader.text = getString(androidx.appcompat.R.string.search_menu_title)
            actionHeader.setCompoundDrawablesWithIntrinsicBounds(
                null,
                null,
                ContextCompat.getDrawable(
                    MovieApplication.application(),
                    R.drawable.icon_search
                ),
                null
            )
        }
    }

    override fun observeViewModel() {
        viewModel.apply {
            moviesNowPlaying.observe(viewLifecycleOwner, EventObserver {
                nowPlayingAdapter.movies = it.movies.orEmpty()
            })
            moviesNowPlaying.observe(viewLifecycleOwner, EventObserver {
                nowPlayingAdapter.movies = it.movies.orEmpty()
            })
            moviesPopular.observe(viewLifecycleOwner, EventObserver {
                popularAdapter.movies = it.movies.orEmpty()
            })

            moviesUpComing.observe(viewLifecycleOwner, EventObserver {
                it.movies?.let { movie ->
                    initIndicatorBanner(movie.size)
                    upComingAdapter.movies = movie.orEmpty()
                }
            })

            statePopular.observe(viewLifecycleOwner, EventObserver {
                popularAdapter.state = it
            })

            stateUpComing.observe(viewLifecycleOwner, EventObserver {
                upComingAdapter.state = it
            })

            stateNowPlaying.observe(viewLifecycleOwner, EventObserver {
                nowPlayingAdapter.state = it
            })

            autoScroll.observe(viewLifecycleOwner, EventObserver {
                binding.banner.setCurrentItem(it, true)
            })
        }
    }

    override fun initControls() {
        binding.banner.apply {
            adapter = upComingAdapter
            isNestedScrollingEnabled = false
            initIndicatorBanner(upComingAdapter.itemCount)

            val screenHeight = resources.displayMetrics.heightPixels
            setPageTransformer(PageTransformer(screenHeight))
        }

        intiRecyclerView(binding.listPopular, popularAdapter)
        intiRecyclerView(binding.listPlayNow, nowPlayingAdapter)
    }

    override fun initEvent() {
        binding.banner.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                viewModel.setIndexAutoScroll(position)
                (binding.indicatorBanner[position] as RadioButton).isChecked = true
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                when (state) {
                    ViewPager2.SCROLL_STATE_DRAGGING, ViewPager2.SCROLL_STATE_SETTLING -> viewModel.cancelAutoScroll()
                    else -> viewModel.startAutoScroll()
                }
            }
        })

        binding.header.actionHeader.setOnClickListener {
            controller.navigate(HomeFragmentDirections.actionHomeToSearch())
        }
    }

    private fun intiRecyclerView(recyclerView: RecyclerView, movieAdapter: MovieAdapter) {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = movieAdapter

            isNestedScrollingEnabled = false
            setHasFixedSize(true)
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

    override fun onResume() {
        super.onResume()
        viewModel.startAutoScroll()
    }
    override fun onPause() {
        super.onPause()
        viewModel.cancelAutoScroll()
    }
}