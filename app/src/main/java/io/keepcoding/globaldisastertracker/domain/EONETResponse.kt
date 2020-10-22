package io.keepcoding.globaldisastertracker.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class EONETResponse(
	val link: String? = null,
	val description: String? = null,
	val title: String? = null,
	val events: List<EventsItem?>? = null
) : Parcelable

@Parcelize
data class CategoriesItem(
	val id: String? = null,
	val title: String? = null
) : Parcelable

@Parcelize
data class GeometryItem(
	val date: String? = null,
	val magnitudeValue: @RawValue Any? = null,
	val coordinates: @RawValue List<Any>? = null,
	val type: String? = null,
	val magnitudeUnit: @RawValue Any? = null
) : Parcelable

@Parcelize
data class EventsItem(
	val sources: List<SourcesItem?>? = null,
	val link: String? = null,
	val description: @RawValue Any? = null,
	val closed: @RawValue Any? = null,
	val geometry: List<GeometryItem?>? = null,
	val id: String? = null,
	val categories: List<CategoriesItem?>? = null,
	val title: String? = null
) : Parcelable

@Parcelize
data class SourcesItem(
	val id: String? = null,
	val url: String? = null
) : Parcelable
