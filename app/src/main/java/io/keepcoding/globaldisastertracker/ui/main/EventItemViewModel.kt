package io.keepcoding.globaldisastertracker.ui.main

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EventItemViewModel(
    var id: String? = null,
    val title: String?,
    val description: String?
) : Parcelable