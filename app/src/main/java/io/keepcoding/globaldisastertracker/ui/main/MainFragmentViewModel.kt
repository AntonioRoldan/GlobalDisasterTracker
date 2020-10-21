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

    private val eventsApi = MutableLiveData<Resource<List<EventsItem?>>>()
    private val eventsLocal = MutableLiveData<Resource<List<DisasterWithImagesAndNews>>>()
    private val events = MutableLiveData<Resource<List<EventItemViewModel?>>>()
    // Get events from server

    fun fetchApiEvents() {
        viewModelScope.launch {
            eventsApi.postValue(Resource.loading(null))
            try{
                val eventsFromApi = apiHelper.getEvents()
                if(eventsFromApi.events != null)
                    eventsApi.postValue(Resource.success(eventsFromApi.events))
            } catch (e: Exception){
                eventsApi.postValue(Resource.error(e.localizedMessage, null))
            }
        }
    }

    fun getApiEvents(): LiveData<Resource<List<EventsItem?>>>{
        return eventsApi
    }

    // Get events from local

    fun fetchLocalEvents(){
        viewModelScope.launch {
            eventsLocal.postValue(Resource.loading(null))
            try {
                val eventsFromLocal = localHelper.getEvents()
                if(eventsFromLocal != null){
                    eventsLocal.postValue(Resource.success(eventsFromLocal))
                }
            } catch (e: Exception){
                eventsLocal.postValue(Resource.error(e.localizedMessage, null))
            }
        }
    }

    fun getLocalEvents(): LiveData<Resource<List<DisasterWithImagesAndNews>>> {
        return eventsLocal
    }

}