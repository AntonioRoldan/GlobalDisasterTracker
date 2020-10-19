package io.keepcoding.globaldisastertracker.domain

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BingImageSearchResponse(

	@field:SerializedName("currentOffset")
	val currentOffset: Int? = null,

	@field:SerializedName("pivotSuggestions")
	val pivotSuggestions: List<PivotSuggestionsItem?>? = null,

	@field:SerializedName("relatedSearches")
	val relatedSearches: List<RelatedSearchesItem?>? = null,

	@field:SerializedName("readLink")
	val readLink: String? = null,

	@field:SerializedName("totalEstimatedMatches")
	val totalEstimatedMatches: Int? = null,

	@field:SerializedName("queryContext")
	val queryContext: QueryContext? = null,

	@field:SerializedName("_type")
	val type: String? = null,

	@field:SerializedName("webSearchUrl")
	val webSearchUrl: String? = null,

	@field:SerializedName("instrumentation")
	val instrumentation: Instrumentation? = null,

	@field:SerializedName("queryExpansions")
	val queryExpansions: List<QueryExpansionsItem?>? = null,

	@field:SerializedName("nextOffset")
	val nextOffset: Int? = null,

	@field:SerializedName("value")
	val value: List<ValueItem?>? = null
) : Parcelable

@Parcelize
data class VideoObject(

	@field:SerializedName("creator")
	val creator: Creator? = null
) : Parcelable

@Parcelize
data class Instrumentation(

	@field:SerializedName("_type")
	val type: String? = null
) : Parcelable

@Parcelize
data class Creator(

	@field:SerializedName("name")
	val name: String? = null
) : Parcelable

@Parcelize
data class ValueItem(

	@field:SerializedName("insightsMetadata")
	val insightsMetadata: InsightsMetadata? = null,

	@field:SerializedName("hostPageUrl")
	val hostPageUrl: String? = null,

	@field:SerializedName("thumbnail")
	val thumbnail: Thumbnail? = null,

	@field:SerializedName("imageId")
	val imageId: String? = null,

	@field:SerializedName("isFamilyFriendly")
	val isFamilyFriendly: Boolean? = null,

	@field:SerializedName("accentColor")
	val accentColor: String? = null,

	@field:SerializedName("hostPageFavIconUrl")
	val hostPageFavIconUrl: String? = null,

	@field:SerializedName("imageInsightsToken")
	val imageInsightsToken: String? = null,

	@field:SerializedName("webSearchUrl")
	val webSearchUrl: String? = null,

	@field:SerializedName("hostPageDomainFriendlyName")
	val hostPageDomainFriendlyName: String? = null,

	@field:SerializedName("datePublished")
	val datePublished: String? = null,

	@field:SerializedName("hostPageDisplayUrl")
	val hostPageDisplayUrl: String? = null,

	@field:SerializedName("contentUrl")
	val contentUrl: String? = null,

	@field:SerializedName("contentSize")
	val contentSize: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("width")
	val width: Int? = null,

	@field:SerializedName("creativeCommons")
	val creativeCommons: String? = null,

	@field:SerializedName("encodingFormat")
	val encodingFormat: String? = null,

	@field:SerializedName("thumbnailUrl")
	val thumbnailUrl: String? = null,

	@field:SerializedName("height")
	val height: Int? = null
) : Parcelable

@Parcelize
data class RelatedSearchesItem(

	@field:SerializedName("displayText")
	val displayText: String? = null,

	@field:SerializedName("searchLink")
	val searchLink: String? = null,

	@field:SerializedName("thumbnail")
	val thumbnail: Thumbnail? = null,

	@field:SerializedName("webSearchUrl")
	val webSearchUrl: String? = null,

	@field:SerializedName("text")
	val text: String? = null
) : Parcelable

@Parcelize
data class InsightsMetadata(

	@field:SerializedName("recipeSourcesCount")
	val recipeSourcesCount: Int? = null,

	@field:SerializedName("pagesIncludingCount")
	val pagesIncludingCount: Int? = null,

	@field:SerializedName("availableSizesCount")
	val availableSizesCount: Int? = null,

	@field:SerializedName("videoObject")
	val videoObject: VideoObject? = null
) : Parcelable

@Parcelize
data class Thumbnail(

	@field:SerializedName("width")
	val width: Int? = null,

	@field:SerializedName("height")
	val height: Int? = null
) : Parcelable

@Parcelize
data class SuggestionsItem(

	@field:SerializedName("displayText")
	val displayText: String? = null,

	@field:SerializedName("searchLink")
	val searchLink: String? = null,

	@field:SerializedName("thumbnail")
	val thumbnail: Thumbnail? = null,

	@field:SerializedName("webSearchUrl")
	val webSearchUrl: String? = null,

	@field:SerializedName("text")
	val text: String? = null
) : Parcelable

@Parcelize
data class PivotSuggestionsItem(

	@field:SerializedName("pivot")
	val pivot: String? = null,

	@field:SerializedName("suggestions")
	val suggestions: List<SuggestionsItem?>? = null
) : Parcelable

@Parcelize
data class QueryContext(

	@field:SerializedName("alterationDisplayQuery")
	val alterationDisplayQuery: String? = null,

	@field:SerializedName("alterationOverrideQuery")
	val alterationOverrideQuery: String? = null,

	@field:SerializedName("originalQuery")
	val originalQuery: String? = null,

	@field:SerializedName("alterationType")
	val alterationType: String? = null,

	@field:SerializedName("alterationMethod")
	val alterationMethod: String? = null
) : Parcelable

@Parcelize
data class QueryExpansionsItem(

	@field:SerializedName("displayText")
	val displayText: String? = null,

	@field:SerializedName("searchLink")
	val searchLink: String? = null,

	@field:SerializedName("thumbnail")
	val thumbnail: Thumbnail? = null,

	@field:SerializedName("webSearchUrl")
	val webSearchUrl: String? = null,

	@field:SerializedName("text")
	val text: String? = null
) : Parcelable
