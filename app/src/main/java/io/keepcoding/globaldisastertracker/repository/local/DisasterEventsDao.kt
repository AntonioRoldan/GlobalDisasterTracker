package io.keepcoding.globaldisastertracker.repository.local


import androidx.lifecycle.LiveData
import androidx.room.*
import io.keepcoding.globaldisastertracker.domain.DisasterEvent
import io.keepcoding.globaldisastertracker.domain.DisasterImage
import io.keepcoding.globaldisastertracker.domain.DisasterNews
import io.keepcoding.globaldisastertracker.domain.DisasterWithImagesAndNews

@Dao
abstract class DisasterEventsDao{

    @Query("SELECT * FROM disaster_event_table ORDER BY datetime(date)")
    abstract fun getEvents(): LiveData<List<DisasterWithImagesAndNews>>

    @Query("SELECT * FROM disaster_event_table WHERE id = :eventId")
    abstract fun getApodId(eventId: String) : LiveData<List<DisasterWithImagesAndNews>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun saveEvent(disasterEvent: DisasterEvent)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun saveImage(image: DisasterImage)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun saveNewsArticle(article: DisasterNews)

    @Delete
    abstract fun deleteEvent(disasterEvent: DisasterEvent)
}
