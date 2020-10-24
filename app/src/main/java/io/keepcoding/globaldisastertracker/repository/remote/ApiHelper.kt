package io.keepcoding.globaldisastertracker.repository.remote

import io.keepcoding.globaldisastertracker.domain.BingImageSearchResponse
import io.keepcoding.globaldisastertracker.domain.BingNewsSearchResponse
import io.keepcoding.globaldisastertracker.domain.EONETResponse
import retrofit2.Call

interface ApiHelper {
    suspend fun getImages(q: String): BingImageSearchResponse

    suspend fun getNews(q: String): BingNewsSearchResponse

    suspend fun getEvents() : EONETResponse
}
