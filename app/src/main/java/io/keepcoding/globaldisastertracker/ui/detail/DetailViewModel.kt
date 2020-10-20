package io.keepcoding.globaldisastertracker.ui.detail

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import io.keepcoding.globaldisastertracker.domain.*
import io.keepcoding.globaldisastertracker.repository.local.DisasterEventsRoomDatabase
import io.keepcoding.globaldisastertracker.repository.remote.RemoteDataManager
import io.keepcoding.globaldisastertracker.utils.ApiKey
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(private val context : Application) : ViewModel() {

    // If the user wants to access the event from the server
    fun getImages(query: String, cb: RemoteDataManager.CallbackResponse<BingImageSearchResponse>) {
        RemoteDataManager().bingSearchApi.getImages(query).enqueue(object : Callback<BingImageSearchResponse>{

            override fun onResponse(call: Call<BingImageSearchResponse>, response: Response<BingImageSearchResponse>) {
                if(response.body() != null && response.isSuccessful)
                    cb.onResponse(response.body()!!)
                else
                    cb.onFailure(Throwable(response.message()), response)
            }

            override fun onFailure(call: Call<BingImageSearchResponse>, t: Throwable) {
                cb.onFailure(Throwable(t))
            }
        })
    }

    fun getNews(query: String, cb: RemoteDataManager.CallbackResponse<BingNewsSearchResponse>) {
        RemoteDataManager().bingSearchApi.getNews(query).enqueue(object : Callback<BingNewsSearchResponse>{
            override fun onResponse(call: Call<BingNewsSearchResponse>, response: Response<BingNewsSearchResponse>){
                if(response.body() != null && response.isSuccessful)
                    cb.onResponse(response.body()!!)
                else
                    cb.onFailure(Throwable(response.message()), response)
            }
            override fun onFailure(call: Call<BingNewsSearchResponse>, t: Throwable) {
                cb.onFailure(Throwable(t))

            }
        })
    }

    fun getEventFromLocal(id: String): LiveData<List<DisasterWithImagesAndNews>> {
        return DisasterEventsRoomDatabase.getInstance(context).disasterEventsDao().getEventById(id)
    }

    fun deleteImage(disasterImage: DisasterImage) {
        DisasterEventsRoomDatabase.getInstance(context).disasterEventsDao().deleteImage(disasterImage)
    }

    fun deleteNewsArticle(distasterNewsArticle: DisasterNews) {
        DisasterEventsRoomDatabase.getInstance(context).disasterEventsDao().deleteNewsArticle(distasterNewsArticle)
    }

    fun deleteEvent(disasterEvent: DisasterEvent){
        DisasterEventsRoomDatabase.getInstance(context).disasterEventsDao().deleteEvent(disasterEvent)
    }
}