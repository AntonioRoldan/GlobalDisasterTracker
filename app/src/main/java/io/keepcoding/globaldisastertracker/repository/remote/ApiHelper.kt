package io.keepcoding.globaldisastertracker.repository.remote

import io.keepcoding.globaldisastertracker.domain.BingImageSearchResponse
import io.keepcoding.globaldisastertracker.domain.BingNewsSearchResponse
import io.keepcoding.globaldisastertracker.domain.EONETResponse

interface ApiHelper {
    suspend fun getImages(q: String): BingImageSearchResponse

    suspend fun getNews(q: String): BingNewsSearchResponse

    suspend fun getEvents() : EONETResponse
}
