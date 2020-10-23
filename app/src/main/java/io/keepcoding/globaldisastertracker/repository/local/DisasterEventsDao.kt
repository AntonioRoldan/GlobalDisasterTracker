package io.keepcoding.globaldisastertracker.repository.local


import androidx.room.*
import io.keepcoding.globaldisastertracker.domain.DisasterEvent
import io.keepcoding.globaldisastertracker.domain.DisasterImage
import io.keepcoding.globaldisastertracker.domain.DisasterNews

@Dao
abstract class DisasterEventsDao{

    @Query("SELECT * FROM disaster_event_table")
    abstract fun getEvents(): List<DisasterEvent>

    @Query("SELECT * FROM disaster_image_table WHERE eventId = :eventId")
    abstract fun getEventImages(eventId: String) : List<DisasterImage>

    @Query("SELECT * FROM disaster_news_table WHERE eventId = :eventId")
    abstract fun getEventNews(eventId: String) : List<DisasterNews>

    @Query("SELECT * FROM disaster_event_table WHERE id = :eventId")
    abstract fun getEvent(eventId: String): DisasterEvent

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertEvent(event: DisasterEvent)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertNews(news: DisasterNews)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertImage(images: DisasterImage)

    @Delete
    abstract fun deleteEvent(event: DisasterEvent)

    @Delete
    abstract fun deleteImage(image: DisasterImage)

    @Delete
    abstract fun deleteNews(news: DisasterNews)

    fun insertEventWithImagesAndNews(event: DisasterEvent){
        var images: List<DisasterImage?>? = event.images
        var news: List<DisasterNews?>? = event.news
        images?.let {
            for(image in it){
                image?.eventId = event.id
                image?.let {
                    insertImage(image)
                }
            }
        }
        news?.let {
            for(article in it){
                article?.eventId = event.id
                article?.let {
                    insertNews(article)
                }
            }
        }
        insertEvent(event)
    }

    fun getEventWithImagesAndNews(id: String): DisasterEvent{
        var event: DisasterEvent = getEvent(id)
        val images: List<DisasterImage> = getEventImages(id)
        val news: List<DisasterNews> = getEventNews(id)
        event.images = images
        event.news = news
        return event
    }

    fun deleteEventWithImagesAndNews(event: DisasterEvent){
        event.images?.let {images ->
            for(image in images){
                image?.let {
                    deleteImage(it)
                }
            }
        }
        event.news?.let {articles ->
            for(article in articles){
                article?.let {
                    deleteNews(it)
                }
            }
        }
        deleteEvent(event)
    }
}
