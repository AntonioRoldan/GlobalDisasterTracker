package io.keepcoding.globaldisastertracker.domain

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "disaster_event_table")
data class DisasterEvent(
    @PrimaryKey var id: String = UUID.randomUUID().toString(),
    @Ignore
    var news: List<DisasterNews?>? = mutableListOf(null),
    @Ignore
    var images: List<DisasterImage?>? = mutableListOf(null),
    var title: String? = null,
    var description: String? = null
)