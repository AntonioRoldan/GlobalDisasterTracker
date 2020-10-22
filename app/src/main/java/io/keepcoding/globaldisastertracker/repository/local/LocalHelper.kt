package io.keepcoding.globaldisastertracker.repository.local

import io.keepcoding.globaldisastertracker.domain.DisasterEvent
import io.keepcoding.globaldisastertracker.domain.DisasterImage
import io.keepcoding.globaldisastertracker.domain.DisasterNews
import io.keepcoding.globaldisastertracker.domain.DisasterWithImagesAndNews

interface LocalHelper {

    suspend fun getEventById(id: String): DisasterEvent

    suspend fun getEvents(): List<DisasterEvent>

    suspend fun saveEvent(disasterEvent: DisasterEvent)

    suspend fun deleteEvent(disasterEvent: DisasterEvent)

}