package com.example.mvvm.ui.view.fragment

import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.mvvm.MovieApplication
import com.example.mvvm.R
import com.example.mvvm.data.enumtype.State
import com.example.mvvm.data.model.Companies
import com.example.mvvm.data.model.Genre
import com.example.mvvm.data.model.MovieDetail
import com.example.mvvm.databinding.FragmentMovieDetailBinding
import com.example.mvvm.databinding.ItemCompaniesBinding
import com.example.mvvm.extension.convert
import com.example.mvvm.extension.formatDate
import com.example.mvvm.extension.orW533xH300
import com.example.mvvm.extension.orW600xH900
import com.example.mvvm.ui.base.BaseFragment
import com.example.mvvm.ui.viewmodel.MovieDetailViewModel
import com.example.mvvm.util.Constant
import com.google.android.material.chip.Chip

class MovieDetailFragment(

    override var layoutResId: Int = R.layout.fragment_movie_detail,

    override var classTypeOfViewModel: Class<MovieDetailViewModel> = MovieDetailViewModel::class.java

) : BaseFragment<FragmentMovieDetailBinding, MovieDetailViewModel>() {

    override fun observeViewModel() {
        viewModel.liveData.observe(viewLifecycleOwner) {
            it?.let {
                setContent(it)
                viewModel.addHistory(it)
            }
        }
        viewModel.stateMovieDetail.observe(viewLifecycleOwner) {
            it?.let {
                when (it) {
                    State.LOADING -> setState(binding.loading)
                    State.SUCCESS -> setState(binding.groupSuccess)
                    State.ERROR -> setState(binding.reload)
                    else -> {}
                }
            }
        }
        viewModel.favorite().observe(viewLifecycleOwner) {
            binding.favourite.isChecked = it != null
        }
    }

    override fun initControls() {
    }

    override fun initEvent() {
        binding.favourite.setOnClickListener {
            viewModel.handleFavorite((it as AppCompatCheckBox).isChecked)
        }
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.reload.setOnClickListener {
            viewModel.getMovieDetail(viewModel.id)
        }
    }

    private fun setState(view: View) {
        val groups = listOf(binding.groupSuccess, binding.loading, binding.reload)
        groups.forEach {
            if (view == it) {
                it.visibility = View.VISIBLE
            } else {
                it.visibility = View.GONE
            }
        }
    }

    private fun setContent(movie: MovieDetail) {
        binding.apply {
            Glide.with(MovieApplication.application())
                .load(movie.backdropPath.orW533xH300())
                .into(binding.imageBackdrop)

            title.text = movie.title
            overview.text = movie.overview
            release.text = movie.releaseDate.formatDate(Constant.YYYY_DD_MM_1)
            voteAverage.text = movie.voteAverage.convert()
            voteCount.text = movie.voteCount.toString()
            tagLine.text = movie.tagline
            runtime.text = movie.runtime.toString()

            setUpGenes(movie.genres)
            setUpCompanies(movie.companies)
        }
    }

    private fun setUpGenes(genres: List<Genre>?) {
        genres?.let {
            if (it.isEmpty()) {
                binding.genesArea.visibility = View.GONE
            } else {
                binding.genesArea.visibility = View.VISIBLE
                it.forEach { data ->
                    val genre = Chip(context)
                    genre.text = data.name
                    genre.setBackgroundColor(
                        ContextCompat.getColor(
                            MovieApplication.application(),
                            R.color.light_gray
                        )
                    )
                    genre.setTextColor(
                        ContextCompat.getColor(
                            MovieApplication.application(),
                            R.color.white
                        )
                    )
                    binding.genes.addView(genre)
                }
            }
        }
    }

    private fun setUpCompanies(companies: List<Companies>?) {
        binding.companies.removeAllViews()
        companies?.let {
            if (it.isEmpty()) {
                binding.companiesTitle.visibility = View.GONE
            } else {
                binding.companiesTitle.visibility = View.VISIBLE
                it.forEach { data ->
                    val view = DataBindingUtil.inflate<ItemCompaniesBinding>(
                        LayoutInflater.from(MovieApplication.application()),
                        R.layout.item_companies,
                        binding.companies,
                        false
                    )
                    Glide.with(MovieApplication.application())
                        .load(data.logoPath.orW600xH900())
                        .into(view.image)
                    view.name.text = data.name
                    binding.companies.addView(view.root)
                }
            }

        }
    }
}