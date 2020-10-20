package io.keepcoding.globaldisastertracker.domain

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.OffsetDateTime
import java.util.*

@Entity(tableName = "Disaster_event_table")
data class DisasterEvent(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val title: String?,
    val description: String?,
    val category: String?,
    val date: OffsetDateTime? = null,
    val latitude: Double? = null,
    val longitude: Double? = null
)