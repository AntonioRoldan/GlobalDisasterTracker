package io.keepcoding.globaldisastertracker.ui.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.keepcoding.globaldisastertracker.domain.DisasterWithImagesAndNews
import io.keepcoding.globaldisastertracker.domain.EONETResponse
import io.keepcoding.globaldisastertracker.repository.local.LocalHelper
import io.keepcoding.globaldisastertracker.repository.remote.ApiHelper
import io.keepcoding.globaldisastertracker.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class MainFragmentViewModel(private val context: Application, private val apiHelper: ApiHelper, private val localHelper: LocalHelper) : ViewModel(){

    private val events = MutableLiveData<Resource<List<EventItemViewModel?>>>()
    // Get events from server

    fun fetchApiEvents() {
        viewModelScope.launch {
            events.postValue(Resource.loading(null))
            try{
                apiHelper.getEvents().enqueue(object : Callback<EONETResponse> {
                    override fun onFailure(call: Call<EONETResponse>, t: Throwable) {
                        events.postValue(Resource.error(t.localizedMessage!!, null))
                    }

                    override fun onResponse(
                        call: Call<EONETResponse>,
                        response: Response<EONETResponse>
                    ) {
                        response.body()?.let {EONETResponse ->
                            val eventViewModels: List<EventItemViewModel?>? = EONETResponse.events?.map {
                                EventItemViewModel(
                                    title = it?.title,
                                    description = it?.description as String?
                                )
                            }
                            events.postValue(Resource.success(eventViewModels))
                        }
                    }

                })
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
                val eventsFromLocal = localHelper.getEvents()
                val eventsViewModels: List<EventItemViewModel?> = eventsFromLocal.map {
                    EventItemViewModel(id = it.id,
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