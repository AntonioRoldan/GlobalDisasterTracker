package io.keepcoding.globaldisastertracker.ui.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.keepcoding.globaldisastertracker.repository.local.LocalHelper
import io.keepcoding.globaldisastertracker.repository.remote.ApiHelper
import io.keepcoding.globaldisastertracker.utils.Resource
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.lang.Exception

class MainFragmentViewModel(private val context: Application, private val apiHelper: ApiHelper, private val localHelper: LocalHelper) : ViewModel(){

    private val events = MutableLiveData<Resource<List<EventItemViewModel?>>>()
    // Get events from server

    fun fetchApiEvents() {
        viewModelScope.launch {
            events.postValue(Resource.loading(null))
            try{
                val response = async {apiHelper.getEvents() }
                val eventViewModels: List<EventItemViewModel?>? = response.await().events?.map {
                    it?.link?.let { link ->
                        EventItemViewModel(
                            url = link,
                            title = it.title,
                            description = it.description as String?
                        )
                    }
                }
                events.postValue(Resource.success(eventViewModels))
            } catch (e: Exception){
                events.postValue(Resource.error(e.localizedMessage!!, null))
            }
        }
    }

    // Get events from local

    fun fetchLocalEvents(){
        viewModelScope.launch {
            events.postValue(Resource.loading(null))
            try {
                val eventsFromLocal = async { localHelper.getEvents() }
                val eventsViewModels: List<EventItemViewModel?> = eventsFromLocal.await().map {
                    EventItemViewModel(id = it.id,
                        url = it.url,
                        title = it.title,
                        description = it.description)
                }
                events.postValue(Resource.success(eventsViewModels))
            } catch (e: Exception){
                events.postValue(Resource.error(e.localizedMessage!!, null))
            }
        }
    }

    // Return observable
    fun getEvents(): LiveData<Resource<List<EventItemViewModel?>>>{
        return events
    }
}