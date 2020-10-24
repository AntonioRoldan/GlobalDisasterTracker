package io.keepcoding.globaldisastertracker.ui.detail

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.keepcoding.globaldisastertracker.domain.DisasterEvent
import io.keepcoding.globaldisastertracker.domain.DisasterImage
import io.keepcoding.globaldisastertracker.domain.DisasterNews
import io.keepcoding.globaldisastertracker.repository.local.LocalHelper
import io.keepcoding.globaldisastertracker.repository.remote.ApiHelper
import io.keepcoding.globaldisastertracker.ui.main.EventItemViewModel
import io.keepcoding.globaldisastertracker.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class DetailFragmentViewModel(private val context : Application, private val apiHelper: ApiHelper, private val localHelper: LocalHelper) : ViewModel() {

    private val images = MutableLiveData<Resource<List<ImageItemViewModel?>>>()
    private val news = MutableLiveData<Resource<List<NewsItemViewModel?>>>()
    private val toast = MutableLiveData<Resource<String?>>()
    // If the user wants to fetch an event from the server

    fun fetchApiImages(query: String) {
        viewModelScope.launch {
            images.postValue(Resource.loading(null))
            try{
                val response = async { apiHelper.getImages(query) }
                val imageViewModels: List<ImageItemViewModel?>? = response.await().value?.map { imageApi ->
                    ImageItemViewModel(image = imageApi?.contentUrl)
                }
                images.postValue(Resource.success(imageViewModels))
            } catch(e: Exception){
                images.postValue(Resource.error(e.localizedMessage!!, null))
            }
        }
    }

    fun fetchApiNews(query: String) {
        viewModelScope.launch {
            news.postValue(Resource.loading(null))
            try {
                val response = async { apiHelper.getNews(query) }
                val newsItemViewModels: List<NewsItemViewModel?>? = response.await().value?.map { articleApi ->
                    NewsItemViewModel(title = articleApi?.name,
                        description = articleApi?.description,
                        newsUrl = articleApi?.url,
                        thumbnail = articleApi?.image?.thumbnail?.contentUrl
                    )
                }
                news.postValue(Resource.success(newsItemViewModels))

            } catch (e: Exception){
                news.postValue(Resource.error(e.localizedMessage!!, null))
            }
        }
    }

    fun getNews(): LiveData<Resource<List<NewsItemViewModel?>>> {
        return news
    }

    fun getImages(): LiveData<Resource<List<ImageItemViewModel?>>> {
        return images
    }

    //If the user is requesting a stored event from local

    fun loadNewsFromLocal(id: String) {
        viewModelScope.launch {
            news.postValue(Resource.loading(null))
            try{
                val event = async { localHelper.getEventById(id) }
                val localNews = event.await().news
                val newsViewModels: List<NewsItemViewModel?>? = localNews?.map {
                    it?.let {
                        NewsItemViewModel(title = it.title, thumbnail = it.thumbnail, description = it.description, newsUrl = it.url)
                    }
                }
                news.postValue(Resource.success(newsViewModels))
            } catch (e: Exception){
                news.postValue(Resource.error(e.localizedMessage!!, null))
            }
        }
    }

    fun loadImagesFromLocal(id: String) {
        viewModelScope.launch {
            images.postValue(Resource.loading(null))
            try{
                val event = async { localHelper.getEventById(id) }
                val localImages = event.await().images
                val imageViewModels: List<ImageItemViewModel?>? = localImages?.map {
                    it?.let {
                        ImageItemViewModel(image = it.url)
                    }
                }
                images.postValue(Resource.success(imageViewModels))
            } catch (e: Exception){
                images.postValue(Resource.error(e.localizedMessage!!, null))
            }
        }
    }

    fun getSnackBar(): LiveData<Resource<String?>>{
        return toast
    }

    fun deleteEvent(id: String){
        viewModelScope.launch {
            try{
                val event = async { localHelper.getEventById(id) }
                localHelper.deleteEvent(event.await())
                images.postValue(Resource.success(null))
                news.postValue(Resource.success(null))
                toast.postValue(Resource.success("Event deleted"))
            } catch (e: Exception){
                toast.postValue(Resource.error(e.localizedMessage!!, "Error while deleting event, try again"))
            }
        }
    }

    fun saveEvent(eventViewModel: EventItemViewModel){
        viewModelScope.launch{
            try{
                val id = UUID.randomUUID().toString()
                // Next we turn the api responses into room entities
                val imageEntities = images.value?.data?.map {
                    it?.let {
                        it.image?.let {url ->
                            DisasterImage(eventId = id, url=url)
                        }
                    }
                }
                val newsEntities = news.value?.data?.map {
                    it?.let{newsItem ->
                        newsItem.title?.let {name ->
                            newsItem.newsUrl.let {url ->
                                newsItem.description?.let{description ->
                                    newsItem.thumbnail?.let {contentUrl ->
                                        DisasterNews(title = name, eventId = id, url = url, description = description, thumbnail = contentUrl)
                                    }
                                }
                            }
                        }
                    }
                }
                val event = DisasterEvent(url = eventViewModel.url, id= id, title = eventViewModel.title, description = eventViewModel.description, news = newsEntities, images = imageEntities)
                var saveEventOperation = async { localHelper.saveEvent(event)  }
                saveEventOperation.await()
                toast.postValue(Resource.success("Event saved"))
            } catch (e: Exception){
                toast.postValue(Resource.error(e.localizedMessage!!, "Could not save disaster, try again"))
            }
        }
    }
}