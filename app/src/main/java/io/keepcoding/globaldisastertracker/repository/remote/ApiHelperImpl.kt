package io.keepcoding.globaldisastertracker.repository.remote

import EONETApi
import io.keepcoding.globaldisastertracker.domain.EONETResponse
import io.keepcoding.globaldisastertracker.utils.ApiKey


class ApiHelperImpl(private val bingSearchApiService: BingSearchApi, private val EONETApiService: EONETApi) : ApiHelper {

    override suspend fun getImages(q: String) = bingSearchApiService.getImages(q)

    override suspend fun getNews(q: String) = bingSearchApiService.getNews(q)

    override suspend fun getEvents() = EONETApiService.getEvents(apiKey = ApiKey.API_KEY)
}