package com.example.mvvm.data.mapper

import com.example.mvvm.data.model.Results
import com.example.mvvm.data.model.MovieItem
import com.example.mvvm.data.model.home.UpComing
import com.example.mvvm.extension.convertPath
import com.example.mvvm.extension.orZero

class UpComingMapper : Mapper<Results, UpComing> {
    override fun mapFrom(item: Results): UpComing {
        return UpComing(
            page = item.page.orZero(),
            totalPage = item.totalPages.orZero(),
            movies = item.results?.map {
                MovieItem(
                    it.id.orZero(),
                    it.posterPath.convertPath(),
                    it.title.orEmpty()
                )
            }.orEmpty()
        )
    }
}