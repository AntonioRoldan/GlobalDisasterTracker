package io.keepcoding.globaldisastertracker.ui.main

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EventItemViewModel(
    val id: String?,
    val title: String?,
    val description: String?
) : Parcelable