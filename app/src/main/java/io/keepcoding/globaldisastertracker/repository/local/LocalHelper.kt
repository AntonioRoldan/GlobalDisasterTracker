package io.keepcoding.globaldisastertracker.repository.local

import io.keepcoding.globaldisastertracker.domain.DisasterEvent

interface LocalHelper {

    suspend fun getEventById(id: String): DisasterEvent

    suspend fun getEvents(): List<DisasterEvent>

    suspend fun saveEvent(disasterEvent: DisasterEvent)

    suspend fun deleteEvent(disasterEvent: DisasterEvent)

}