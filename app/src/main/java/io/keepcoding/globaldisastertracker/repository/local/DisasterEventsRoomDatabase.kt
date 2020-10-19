package io.keepcoding.globaldisastertracker.repository.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import io.keepcoding.globaldisastertracker.domain.DisasterEvent
import io.keepcoding.globaldisastertracker.domain.DisasterImage
import io.keepcoding.globaldisastertracker.domain.DisasterNews

@Database(entities = [DisasterEvent::class, DisasterImage::class, DisasterNews::class], version = 1, exportSchema = false)
abstract class DisasterEventsRoomDatabase : RoomDatabase(){

    abstract fun disasterEventsDao() : DisasterEventsDao

    companion object {

        private var instance : DisasterEventsRoomDatabase? = null

        fun getInstance(context: Context): DisasterEventsRoomDatabase {

            if(instance == null){

                synchronized(DisasterEventsRoomDatabase::class) {
                    instance = Room.databaseBuilder(context.applicationContext, DisasterEventsRoomDatabase::class.java,
                        "disasters_events_db")
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }

            return instance!!
        }
    }
}