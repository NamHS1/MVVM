package com.example.mvvm.data.mapper

import com.example.mvvm.data.model.Results
import com.example.mvvm.extension.*
import com.example.mvvm.ui.model.MovieResponse
import com.example.mvvm.util.Constant

class MovieMapper : Mapper<Results, List<MovieResponse>> {
    override fun mapFrom(item: Results): List<MovieResponse> {
        return item.results?.map {
            MovieResponse(
                id = it.id.orZero(),
                title = it.title.orEmpty(),
                posterPath = it.posterPath.orW600xH900(),
                backdropPath = it.backdropPath.orW533xH300(),
                release = it.releaseDate.formatDate(Constant.YYYY_DD_MM_1),
                voteAverage = it.voteAverage.rating()
            )
        }.orEmpty()
    }
}