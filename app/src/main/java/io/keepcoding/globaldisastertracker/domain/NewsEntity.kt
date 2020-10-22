package io.keepcoding.globaldisastertracker.domain


import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.OffsetDateTime
import java.util.*

@Entity(tableName = "disaster_news_table")
data class DisasterNews(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    var eventId: String,
    val title: String? = null,
    val description: String? = null,
    val url: String? = null,
    val thumbnail: String? = null
)

