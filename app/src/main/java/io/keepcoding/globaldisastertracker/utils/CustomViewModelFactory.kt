package io.keepcoding.globaldisastertracker.utils

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.keepcoding.globaldisastertracker.repository.local.LocalHelper
import io.keepcoding.globaldisastertracker.repository.remote.ApiHelper
import io.keepcoding.globaldisastertracker.ui.detail.DetailViewModel
import io.keepcoding.globaldisastertracker.ui.main.MainFragmentViewModel

import java.lang.IllegalArgumentException

class CustomViewModelFactory(private val application: Application, private val apiHelper: ApiHelper, private val localHelper: LocalHelper) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return with(modelClass) {
            when {
                isAssignableFrom(MainFragmentViewModel::class.java) -> MainFragmentViewModel(application, apiHelper, localHelper)
                isAssignableFrom(DetailViewModel::class.java) -> DetailViewModel(application, apiHelper, localHelper)
                else -> throw IllegalArgumentException("Unknown ViewModel")
            }
        } as T
    }
}
