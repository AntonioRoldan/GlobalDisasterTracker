package io.keepcoding.globaldisastertracker.repository.local

import io.keepcoding.globaldisastertracker.domain.DisasterEvent
import io.keepcoding.globaldisastertracker.domain.DisasterImage
import io.keepcoding.globaldisastertracker.domain.DisasterNews
import io.keepcoding.globaldisastertracker.domain.DisasterWithImagesAndNews

class LocalHelperImpl(private val appDatabase: DisasterEventsRoomDatabase) : LocalHelper {

    override suspend fun getEventById(id: String): DisasterEvent = appDatabase.disasterEventsDao().getEvent(id)

    override suspend fun getEvents(): List<DisasterEvent> = appDatabase.disasterEventsDao().getEvents()

    override suspend fun saveEvent(disasterEvent: DisasterEvent) {
        appDatabase.disasterEventsDao().insertEventWithImagesAndNews(disasterEvent)
    }

    override suspend fun deleteEvent(event: DisasterEvent) {
        appDatabase.disasterEventsDao().deleteEventWithImagesAndNews(event)
    }

}