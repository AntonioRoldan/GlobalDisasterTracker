package io.keepcoding.globaldisastertracker.repository.local


import androidx.lifecycle.LiveData
import androidx.room.*
import io.keepcoding.globaldisastertracker.domain.DisasterEvent
import io.keepcoding.globaldisastertracker.domain.DisasterImage
import io.keepcoding.globaldisastertracker.domain.DisasterNews
import io.keepcoding.globaldisastertracker.domain.DisasterWithImagesAndNews

@Dao
abstract class DisasterEventsDao{

    @Query("SELECT * FROM disaster_event_table")
    abstract fun getEvents(): List<DisasterWithImagesAndNews>

    @Query("SELECT * FROM disaster_event_table WHERE id = :eventId")
    abstract fun getEventById(eventId: String) : DisasterWithImagesAndNews

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun saveEvent(disasterEvent: DisasterWithImagesAndNews)

    @Delete
    abstract fun deleteEvent(disasterEvent: DisasterWithImagesAndNews)

}
