package io.keepcoding.globaldisastertracker.domain


import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "disaster_image_table")
data class DisasterImage(
    @PrimaryKey var id: String = UUID.randomUUID().toString(),
    var eventId: String? = null,
    var url: String
)