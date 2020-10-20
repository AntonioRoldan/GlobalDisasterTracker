package io.keepcoding.globaldisastertracker.ui.detail

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import io.keepcoding.globaldisastertracker.domain.*
import io.keepcoding.globaldisastertracker.repository.local.LocalHelper
import io.keepcoding.globaldisastertracker.repository.remote.ApiHelper
import io.keepcoding.globaldisastertracker.utils.Resource
import kotlinx.coroutines.launch
import java.time.OffsetDateTime

class DetailViewModel(private val context : Application, private val apiHelper: ApiHelper, private val localHelper: LocalHelper) : ViewModel() {

    private val apiImages = MutableLiveData<Resource<List<ValueItem?>>>()
    private val apiNews = MutableLiveData<Resource<List<ValueNewsItem?>>>()
    private val localImages = MutableLiveData<Resource<List<DisasterImage?>>>()
    private val localNews = MutableLiveData<Resource<List<DisasterNews?>>>()
    private val toast = MutableLiveData<Resource<String>>()
    // If the user wants to fetch an event from the server

    fun fetchApiImages(query: String) {
        viewModelScope.launch {
            apiImages.postValue(Resource.loading(null))
            try{
                val imagesFromApi = apiHelper.getImages(query)
                apiImages.postValue(Resource.success(imagesFromApi.value))
            } catch(e: Exception){
                apiImages.postValue(Resource.error(e.localizedMessage, null))
            }
        }
    }

    fun fetchApiNews(query: String) {
        viewModelScope.launch {
            apiNews.postValue(Resource.loading(null))
            try {
                val newsFromApi = apiHelper.getNews(query)
                apiNews.postValue(Resource.success(newsFromApi.value))
            } catch (e: Exception){
                apiNews.postValue(Resource.error(e.localizedMessage, null))
            }
        }
    }

    fun getApiNews(): LiveData<Resource<List<ValueNewsItem?>>> {
        return apiNews
    }

    fun getApiImages(): LiveData<Resource<List<ValueItem?>>> {
        return apiImages
    }

    //If the user is requesting a stored event from local

    fun loadNewsFromLocal(id: String) {
        viewModelScope.launch {
            localNews.postValue(Resource.loading(null))
            try{
                val event = localHelper.getEventById(id)
                val news = event.news
                localNews.postValue(Resource.success(news))
            } catch (e: Exception){
                localNews.postValue(Resource.error(e.localizedMessage, null))
            }
        }
    }

    fun loadImagesFromLocal(id: String) {
        viewModelScope.launch {
            localImages.postValue(Resource.loading(null))
            try{
                val event = localHelper.getEventById(id)
                val images = event.images
                localImages.postValue(Resource.success(images))
            } catch (e: Exception){
                localImages.postValue(Resource.error(e.localizedMessage, null))
            }
        }
    }

    fun getLocalImages(): LiveData<Resource<List<DisasterImage?>>> {
        return localImages
    }

    fun getLocalNews(): LiveData<Resource<List<DisasterNews?>>> {
        return localNews
    }

    fun deleteEvent(id: String){
        viewModelScope.launch {
            try{
                val event = localHelper.getEventById(id)
                localHelper.deleteEvent(event)
                localImages.postValue(Resource.success(null))
                localNews.postValue(Resource.success(null))
            } catch (e: Exception){
                localImages.postValue(Resource.error(e.localizedMessage, null))
                localNews.postValue(Resource.error(e.localizedMessage, null))
            }
        }
    }

    fun saveEvent(EONETDto: EventsItem){
        viewModelScope.launch{
            try{
                val event = DisasterEvent(title = EONETDto.title,
                    description = EONETDto.description as String?,
                    category = EONETDto.categories?.get(0)?.title,
                    latitude = EONETDto.geometry?.get(0)?.coordinates?.get(0),
                    longitude = EONETDto.geometry?.get(0)?.coordinates?.get(1)
                    )
                // Next we turn the api responses into room entities
                val images = apiImages.value?.data?.map {
                    it?.let {
                        it.thumbnailUrl?.let { url -> DisasterImage(url = url) }
                    }
                }
                val news = apiNews.value?.data?.map {
                    it?.let{newsItem ->
                        newsItem.name?.let {name ->
                            newsItem.url?.let {url ->
                                newsItem.description?.let{description ->
                                    newsItem.image?.thumbnail?.contentUrl?.let {contentUrl ->
                                        DisasterNews(title = name, url = url, description = description, thumbnail = contentUrl)
                                    }
                                }
                            }
                        }
                    }
                }
                val eventWithRelations = DisasterWithImagesAndNews(event, images, news)
                localHelper.saveEvent(eventWithRelations)
                toast.postValue(Resource.success("Event saved"))
            } catch (e: Exception){
                apiImages.postValue(Resource.error(e.localizedMessage, null))
                apiNews.postValue(Resource.error(e.localizedMessage, null))
            }
        }
    }
}