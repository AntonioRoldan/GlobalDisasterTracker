package io.keepcoding.globaldisastertracker.repository.local

import io.keepcoding.globaldisastertracker.domain.DisasterEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocalHelperImpl(private val appDatabase: DisasterEventsRoomDatabase) : LocalHelper {

    override suspend fun getEventById(id: String): DisasterEvent{
        var event: DisasterEvent
        withContext(Dispatchers.IO){
            event = appDatabase.disasterEventsDao().getEventWithImagesAndNews(id)
        }
        return event
    }

    override suspend fun getEvents(): List<DisasterEvent>{
        var events: List<DisasterEvent>
        withContext(Dispatchers.IO){
            events = appDatabase.disasterEventsDao().getEvents()
        }
        return events
    }

    override suspend fun saveEvent(disasterEvent: DisasterEvent) {
        withContext(Dispatchers.IO){
            appDatabase.disasterEventsDao().insertEventWithImagesAndNews(disasterEvent)
        }
    }

    override suspend fun deleteEvent(disasterEvent: DisasterEvent) {
        withContext(Dispatchers.IO){
            appDatabase.disasterEventsDao().deleteEventWithImagesAndNews(disasterEvent)
        }
    }

}