package io.keepcoding.globaldisastertracker.ui.main

interface CallbackItemClick {
    fun onItemClickFromServer(eventItemViewModel: EventItemViewModel)
    fun onItemClickFromLocal(eventItemViewModel: EventItemViewModel)
}