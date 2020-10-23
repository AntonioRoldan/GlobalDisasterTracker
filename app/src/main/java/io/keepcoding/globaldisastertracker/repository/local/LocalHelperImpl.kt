package io.keepcoding.globaldisastertracker.repository.local

import io.keepcoding.globaldisastertracker.domain.DisasterEvent

class LocalHelperImpl(private val appDatabase: DisasterEventsRoomDatabase) : LocalHelper {

    override suspend fun getEventById(id: String): DisasterEvent = appDatabase.disasterEventsDao().getEventWithImagesAndNews(id)

    override suspend fun getEvents(): List<DisasterEvent> = appDatabase.disasterEventsDao().getEvents()

    override suspend fun saveEvent(disasterEvent: DisasterEvent) {
        appDatabase.disasterEventsDao().insertEventWithImagesAndNews(disasterEvent)
    }

    override suspend fun deleteEvent(disasterEvent: DisasterEvent) {
        appDatabase.disasterEventsDao().deleteEventWithImagesAndNews(disasterEvent)
    }

}