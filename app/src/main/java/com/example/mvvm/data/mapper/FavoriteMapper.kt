package com.example.mvvm.data.mapper

import com.example.mvvm.data.database.entity.Favorite
import com.example.mvvm.data.model.MovieDetail
import com.example.mvvm.extension.formatDate
import com.example.mvvm.extension.orW600xH900
import com.example.mvvm.extension.orZero
import com.example.mvvm.extension.rating
import com.example.mvvm.util.Constant

object FavoriteMapper : Mapper<MovieDetail, Favorite> {
    override fun mapFrom(item: MovieDetail): Favorite {
        return Favorite(
            id = item.id.orZero(),
            title = item.title.orEmpty(),
            posterPath = item.posterPath.orW600xH900(),
            release = item.releaseDate.formatDate(Constant.YYYY_DD_MM_1),
            voteAverage = item.voteAverage.rating()
        )
    }
}