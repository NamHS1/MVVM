package com.example.mvvm.data.mapper

import com.example.mvvm.data.model.Results
import com.example.mvvm.data.model.MovieItem
import com.example.mvvm.data.model.home.Popular
import com.example.mvvm.extension.w600xh900
import com.example.mvvm.extension.orZero

class PopularMapper : Mapper<Results, Popular> {
    override fun mapFrom(item: Results): Popular {
        return Popular(
            page = item.page.orZero(),
            totalPage = item.totalPages.orZero(),
            movies = item.results?.map {
                MovieItem(
                    id = it.id.orZero(),
                    imagePath = it.posterPath.w600xh900(),
                    title = it.title.orEmpty()
                )
            }.orEmpty()
        )
    }
}