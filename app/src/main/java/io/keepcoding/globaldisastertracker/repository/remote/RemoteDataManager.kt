package io.keepcoding.globaldisastertracker.repository.remote

import EONETApi


import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RemoteDataManager {

    interface CallbackResponse<T> {
        fun onResponse(response: T)
        fun onFailure(t: Throwable, res: Response<*>? = null)
    }

    val eonetApi: EONETApi
    val bingSearchApi: BingSearchApi

    init {

        val timeout: Long = 6 * 1000

        val client = OkHttpClient.Builder()
            .connectTimeout(timeout, TimeUnit.MILLISECONDS)
            .writeTimeout(timeout, TimeUnit.MILLISECONDS)
            .readTimeout(timeout, TimeUnit.MILLISECONDS)
            .build()

        val retrofitEONET = Retrofit.Builder()
            //.client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://eonet.sci.gsfc.nasa.gov/api/v3")
            .build()
        val retrofitSearch = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.cognitive.microsoft.com/bing/v7.0")
            .build()



        eonetApi = retrofitEONET.create(EONETApi::class.java)
        bingSearchApi = retrofitSearch.create(BingSearchApi::class.java)
    }
}
