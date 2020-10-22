package io.keepcoding.globaldisastertracker.ui.detail

interface DetailInteractionListener {
    fun onImageItemClick(imageUrl: String)
    fun onNewsItemClick(newsArticleUrl: String)
}