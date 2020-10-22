package io.keepcoding.globaldisastertracker.domain


import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.OffsetDateTime
import java.util.*

@Entity(tableName = "disaster_news_table")
data class DisasterNews(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    var eventId: String? = null,
    var title: String? = null,
    var description: String? = null,
    var url: String? = null,
    var thumbnail: String? = null
)

