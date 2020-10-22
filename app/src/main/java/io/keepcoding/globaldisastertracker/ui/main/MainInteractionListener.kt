package io.keepcoding.globaldisastertracker.ui.main

interface MainInteractionListener {
    fun onItemClickFromServer(eventItemViewModel: EventItemViewModel)
    fun onItemClickFromLocal(eventItemViewModel: EventItemViewModel)
}