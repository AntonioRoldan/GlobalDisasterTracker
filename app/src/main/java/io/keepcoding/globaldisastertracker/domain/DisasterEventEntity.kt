package io.keepcoding.globaldisastertracker.domain

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.time.OffsetDateTime
import java.util.*

@Entity(tableName = "disaster_event_table")
data class DisasterEvent(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    @Ignore
    var news: List<DisasterNews?>?,
    @Ignore
    var images: List<DisasterImage?>?,
    val title: String?,
    val description: String?
)