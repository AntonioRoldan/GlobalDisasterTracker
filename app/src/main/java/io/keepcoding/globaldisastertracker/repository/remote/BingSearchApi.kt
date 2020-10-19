package io.keepcoding.globaldisastertracker.repository.remote

import io.keepcoding.globaldisastertracker.domain.BingImageSearchResponse
import io.keepcoding.globaldisastertracker.domain.BingNewsSearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query



interface BingSearchApi {

    @GET("/images/search")  // planetary/apod?api_key=DEMO_KEY
    @Headers("Content-Type: application/json",
        "Ocp-Apim-Subscription-Key: 64f88d4b875840e489f51c8885192e66"
    )
    fun getImages(@Query("q") q: String): Call<BingImageSearchResponse>

    @GET("/news/search")  // planetary/apod?api_key=DEMO_KEY
    @Headers("Content-Type: application/json",
        "Ocp-Apim-Subscription-Key: 64f88d4b875840e489f51c8885192e66"
    )
    fun getNews(@Query("q") q: String): Call<BingNewsSearchResponse>

}
