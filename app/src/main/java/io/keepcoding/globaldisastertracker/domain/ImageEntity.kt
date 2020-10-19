package io.keepcoding.globaldisastertracker.domain


import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "Disaster_image_table")
data class DisasterImage(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val url: String
)