package io.keepcoding.globaldisastertracker.repository.remote

import io.keepcoding.globaldisastertracker.domain.BingImageSearchResponse
import io.keepcoding.globaldisastertracker.domain.BingNewsSearchResponse
import io.keepcoding.globaldisastertracker.domain.EONETResponse
import io.keepcoding.globaldisastertracker.utils.ApiKey
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class ApiHelperImpl(private val bingSearchApiService: BingSearchApi, private val EONETApiService: EONETApi) : ApiHelper {

    override suspend fun getImages(q: String) : BingImageSearchResponse{
        var response: BingImageSearchResponse
        withContext(Dispatchers.IO) {// IO thread
            response = bingSearchApiService.getImages(q)
        } // Main thread
        return response
    }

    override suspend fun getNews(q: String): BingNewsSearchResponse {
        var response: BingNewsSearchResponse
        withContext(Dispatchers.IO){
            response = bingSearchApiService.getNews(q)
        }
        return response
    }

    override suspend fun getEvents(): EONETResponse {
        var response: EONETResponse
        withContext(Dispatchers.IO){
            response = EONETApiService.getEvents(apiKey = ApiKey.API_KEY)
        }
        return response
    }
}