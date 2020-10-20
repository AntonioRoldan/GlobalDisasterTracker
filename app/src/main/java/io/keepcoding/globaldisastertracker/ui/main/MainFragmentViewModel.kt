package io.keepcoding.globaldisastertracker.ui.main

import android.app.Application
import androidx.lifecycle.ViewModel
import io.keepcoding.globaldisastertracker.domain.EONETResponse
import io.keepcoding.globaldisastertracker.repository.remote.RemoteDataManager
import io.keepcoding.globaldisastertracker.utils.ApiKey
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainFragmentViewModel(private val context: Application) : ViewModel(){

    fun getEvents(cb: RemoteDataManager.CallbackResponse<EONETResponse>) {
        RemoteDataManager().eonetApi.getEvents(ApiKey.API_KEY).enqueue(object :
            Callback<EONETResponse> {

            override fun onResponse(call: Call<EONETResponse>, response: Response<EONETResponse>) {
                if(response.body() != null && response.isSuccessful)
                    cb.onResponse(response.body()!!)
                else
                    cb.onFailure(Throwable(response.message()), response)
            }

            override fun onFailure(call: Call<EONETResponse>, t: Throwable) {
                cb.onFailure(Throwable(t))
            }
        })
    }
}