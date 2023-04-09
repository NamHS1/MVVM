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
import com.example.mvvm.data.enumtype.MovieType
import com.example.mvvm.databinding.FragmentHomeBinding
import com.example.mvvm.extension.addLoadStateListener
import com.example.mvvm.ui.base.BaseFragment
import com.example.mvvm.ui.adapter.MovieAdapter
import com.example.mvvm.ui.adapter.loadstate.HomeLoadStateAdapter
import com.example.mvvm.ui.viewmodel.HomeViewModel
import com.example.mvvm.util.EventObserver
import com.example.mvvm.util.PageTransformer

class HomeFragment(

    override var layoutResId: Int = R.layout.fragment_home,

    override var classTypeOfViewModel: Class<HomeViewModel> = HomeViewModel::class.java

) : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    private val popularAdapter by lazy {
        MovieAdapter(
            ItemMovieType.SMALL,
            actionMoveDetail = {
                controller.navigate(HomeFragmentDirections.actionMoveToMovieDetail(id = it))
            }
        )
    }
    private val nowPlayingAdapter by lazy {
        MovieAdapter(
            ItemMovieType.SMALL,
            actionMoveDetail = {
                controller.navigate(HomeFragmentDirections.actionMoveToMovieDetail(id = it))
            }
        )
    }
    private val upComingAdapter by lazy {
        MovieAdapter(
            ItemMovieType.BIG,
            actionMoveDetail = {
                controller.navigate(HomeFragmentDirections.actionMoveToMovieDetail(id = it))
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
                nowPlayingAdapter.submitData(lifecycle, it)
            })
            moviesUpComing.observe(viewLifecycleOwner, EventObserver {
                upComingAdapter.submitData(lifecycle, it)
            })
            moviesPopular.observe(viewLifecycleOwner, EventObserver {
                popularAdapter.submitData(lifecycle, it)
            })

            autoScroll.observe(viewLifecycleOwner, EventObserver {
                binding.banner.setCurrentItem(it, true)
            })
        }
    }

    override fun initControls() {
        binding.banner.apply {
            adapter = upComingAdapter
            offscreenPageLimit = 3
            isNestedScrollingEnabled = false

            val screenHeight = resources.displayMetrics.heightPixels
            setPageTransformer(PageTransformer(screenHeight))
        }

        intiRecyclerView(binding.listPopular, popularAdapter)
        intiRecyclerView(binding.listPlayNow, nowPlayingAdapter)

        setStatePage()
    }

    private fun setStatePage() {
        upComingAdapter.addLoadStateListener(
            root = binding.bannerState,
            type = MovieType.UPCOMING
        ) {
            viewModel.bannerSize = it
            initIndicatorBanner(it)
        }

        nowPlayingAdapter.addLoadStateListener(
            root = binding.nowPlayingState
        ) {}
        popularAdapter.addLoadStateListener(
            root = binding.popularState
        ) {}
    }

    override fun initEvent() {
        binding.banner.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (binding.indicatorBanner.childCount > 0) {
                    viewModel.indexAutoScroll = position
                    (binding.indicatorBanner[position] as RadioButton).isChecked = true
                }
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                when (state) {
                    ViewPager2.SCROLL_STATE_DRAGGING,
                    ViewPager2.SCROLL_STATE_SETTLING -> {
                        viewModel.cancelAutoScroll()
                    }
                    else -> {
                        viewModel.startAutoScroll()
                    }
                }
            }
        })

        binding.header.actionHeader.setOnClickListener {
            controller.navigate(HomeFragmentDirections.actionHomeToSearch())
        }
    }

    private fun intiRecyclerView(recyclerView: RecyclerView, movieAdapter: MovieAdapter) {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false)
            adapter = movieAdapter.withLoadStateFooter(
                footer = HomeLoadStateAdapter {
                    movieAdapter.retry()
                }
            )

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