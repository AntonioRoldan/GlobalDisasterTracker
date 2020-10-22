package io.keepcoding.globaldisastertracker.ui.detail

data class NewsItemViewModel(val viewType: Int = DetailAdapter.NEWS,val title: String?, val thumbnail: String?, val description: String?, val newsUrl: String?)

