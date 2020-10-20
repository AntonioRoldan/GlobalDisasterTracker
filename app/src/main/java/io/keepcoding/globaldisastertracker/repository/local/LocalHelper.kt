package io.keepcoding.globaldisastertracker.repository.local

import io.keepcoding.globaldisastertracker.domain.DisasterEvent
import io.keepcoding.globaldisastertracker.domain.DisasterImage
import io.keepcoding.globaldisastertracker.domain.DisasterNews
import io.keepcoding.globaldisastertracker.domain.DisasterWithImagesAndNews

interface LocalHelper {

    suspend fun getEventById(id: String): DisasterWithImagesAndNews

    suspend fun getEvents(): List<DisasterWithImagesAndNews>

    suspend fun saveEvent(disasterEvent: DisasterWithImagesAndNews)

    suspend fun deleteEvent(disasterEvent: DisasterWithImagesAndNews)

}