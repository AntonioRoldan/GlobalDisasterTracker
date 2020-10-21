package io.keepcoding.globaldisastertracker.ui.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.keepcoding.globaldisastertracker.domain.DisasterWithImagesAndNews
import io.keepcoding.globaldisastertracker.domain.EventsItem
import io.keepcoding.globaldisastertracker.repository.local.DisasterEventsRoomDatabase
import io.keepcoding.globaldisastertracker.repository.local.LocalHelper
import io.keepcoding.globaldisastertracker.repository.remote.ApiHelper
import io.keepcoding.globaldisastertracker.utils.Resource
import kotlinx.coroutines.launch
import java.lang.Exception

class MainFragmentViewModel(private val context: Application, private val apiHelper: ApiHelper, private val localHelper: LocalHelper) : ViewModel(){

    private val eventsLocal = MutableLiveData<Resource<List<DisasterWithImagesAndNews>>>()
    private val events = MutableLiveData<Resource<List<EventItemViewModel?>>>()
    // Get events from server

    fun fetchApiEvents() {
        viewModelScope.launch {
            events.postValue(Resource.loading(null))
            try{
                val eventsFromApi = apiHelper.getEvents()
                if(eventsFromApi.events != null) {
                    val eventsViewModels: List<EventItemViewModel?>? = eventsFromApi.events?.map {
                        it?.let {eventDto ->
                            EventItemViewModel(id = null, title = eventDto.title, description = eventDto.description as String?)
                        }
                    }
                    events.postValue(Resource.success(eventsViewModels))
                }
            } catch (e: Exception){
                events.postValue(Resource.error(e.localizedMessage, null))
            }
        }
    }

    // Get events from local

    fun fetchLocalEvents(){
        viewModelScope.launch {
            events.postValue(Resource.loading(null))
            try {
                val eventsFromLocal = localHelper.getEvents()
                val eventsViewModels: List<EventItemViewModel?> = eventsFromLocal.map {
                    EventItemViewModel(id = it.DisasterEventEntity.id,
                        title = it.DisasterEventEntity.title,
                        description = it.DisasterEventEntity.description)
                }
                events.postValue(Resource.success(eventsViewModels))
            } catch (e: Exception){
                events.postValue(Resource.error(e.localizedMessage, null))
            }
        }
    }

    // Return observable
    fun getEvents(): LiveData<Resource<List<EventItemViewModel?>>>{
        return events
    }
}