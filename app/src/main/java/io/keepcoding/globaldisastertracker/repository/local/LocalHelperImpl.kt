package io.keepcoding.globaldisastertracker.repository.local

import io.keepcoding.globaldisastertracker.domain.DisasterEvent
import io.keepcoding.globaldisastertracker.domain.DisasterImage
import io.keepcoding.globaldisastertracker.domain.DisasterNews
import io.keepcoding.globaldisastertracker.domain.DisasterWithImagesAndNews

class LocalHelperImpl(private val appDatabase: DisasterEventsRoomDatabase) : LocalHelper {

    override suspend fun getEventById(id: String): DisasterWithImagesAndNews = appDatabase.disasterEventsDao().getEventById(id)

    override suspend fun getEvents(): List<DisasterWithImagesAndNews> = appDatabase.disasterEventsDao().getEvents()

    override suspend fun saveEvent(disasterEvent: DisasterWithImagesAndNews) {
        appDatabase.disasterEventsDao().saveEvent(disasterEvent)
    }

    override suspend fun deleteEvent(disasterEvent: DisasterWithImagesAndNews) {
        appDatabase.disasterEventsDao().deleteEvent(disasterEvent)
    }


}