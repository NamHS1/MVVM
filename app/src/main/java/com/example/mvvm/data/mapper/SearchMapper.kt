package com.example.mvvm.data.mapper

import com.example.mvvm.data.model.Results
import com.example.mvvm.data.model.SearchItem
import com.example.mvvm.data.model.search.Search
import com.example.mvvm.extension.*
import com.example.mvvm.util.Constant
import kotlin.collections.orEmpty

class SearchMapper : Mapper<Results, Search> {
    override fun mapFrom(item: Results): Search {
        return Search(
            page = item.page.orZero(),
            totalPage =  item.totalPages.orZero(),
            movies = item.results?.map {
                SearchItem(
                    id = it.id.orZero(),
                    imagePath = it.posterPath.orW600xH900(),
                    title = it.title.orEmpty(),
                    release = it.releaseDate.formatDate(Constant.YYYY_DD_MM_1),
                    voteAverage = it.voteAverage.rating()
                )
            }.orEmpty()
        )
    }
}