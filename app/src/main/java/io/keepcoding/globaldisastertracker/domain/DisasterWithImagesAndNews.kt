
package io.keepcoding.globaldisastertracker.domain

import androidx.room.Embedded
import androidx.room.Relation

data class DisasterWithImagesAndNews(
    // We define two one to many relations: one between events and images, and the other between events and news articles
    @Embedded val DisasterEventEntity: DisasterEvent,
    @Relation(
        parentColumn = "id",
        entityColumn = "id"
    )
    val images : List<DisasterImage?>?,
    @Relation(
        parentColumn = "id",
        entityColumn = "id"
    )
    val news: List<DisasterNews?>?
)