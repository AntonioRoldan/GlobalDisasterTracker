package io.keepcoding.globaldisastertracker.domain

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BingNewsSearchResponse(

	@field:SerializedName("readLink")
	val readLink: String? = null,

	@field:SerializedName("totalEstimatedMatches")
	val totalEstimatedMatches: Int? = null,

	@field:SerializedName("queryContext")
	val queryContext: QueryContext? = null,

	@field:SerializedName("_type")
	val type: String? = null,

	@field:SerializedName("sort")
	val sort: List<SortItem?>? = null,

	@field:SerializedName("value")
	val value: List<ValueItem?>? = null
) : Parcelable

@Parcelize
data class Image(

	@field:SerializedName("thumbnail")
	val thumbnail: Thumbnail? = null
) : Parcelable

@Parcelize
data class MentionsItem(

	@field:SerializedName("name")
	val name: String? = null
) : Parcelable

@Parcelize
data class ValueItem(

	@field:SerializedName("datePublished")
	val datePublished: String? = null,

	@field:SerializedName("image")
	val image: Image? = null,

	@field:SerializedName("provider")
	val provider: List<ProviderItem?>? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("about")
	val about: List<AboutItem?>? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("url")
	val url: String? = null,

	@field:SerializedName("category")
	val category: String? = null,

	@field:SerializedName("mentions")
	val mentions: List<MentionsItem?>? = null
) : Parcelable

@Parcelize
data class SortItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("isSelected")
	val isSelected: Boolean? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("url")
	val url: String? = null
) : Parcelable

@Parcelize
data class ProviderItem(

	@field:SerializedName("image")
	val image: Image? = null,

	@field:SerializedName("_type")
	val type: String? = null,

	@field:SerializedName("name")
	val name: String? = null
) : Parcelable

@Parcelize
data class AboutItem(

	@field:SerializedName("readLink")
	val readLink: String? = null,

	@field:SerializedName("name")
	val name: String? = null
) : Parcelable

@Parcelize
data class Thumbnail(

	@field:SerializedName("contentUrl")
	val contentUrl: String? = null
) : Parcelable

@Parcelize
data class QueryContext(

	@field:SerializedName("adultIntent")
	val adultIntent: Boolean? = null,

	@field:SerializedName("originalQuery")
	val originalQuery: String? = null
) : Parcelable
