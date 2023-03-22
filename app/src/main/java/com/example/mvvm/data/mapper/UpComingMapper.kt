package com.example.mvvm.data.mapper

import com.example.mvvm.data.model.Results
import com.example.mvvm.data.model.MovieItem
import com.example.mvvm.data.model.home.UpComing
import com.example.mvvm.extension.orZero
import com.example.mvvm.extension.orW533xH300

class UpComingMapper : Mapper<Results, UpComing> {
    override fun mapFrom(item: Results): UpComing {
        return UpComing(
            page = item.page.orZero(),
            totalPage = item.totalPages.orZero(),
            movies = item.results?.map {
                MovieItem(
                    id = it.id.orZero(),
                    imagePath = it.posterPath.orW533xH300(),
                    title = it.title.orEmpty()
                )
            }.orEmpty()
        )
    }
}