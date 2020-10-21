package io.keepcoding.globaldisastertracker.ui.detail

interface CallbackItemClick {
    fun onImageItemClick(imageUrl: String)
    fun onNewsItemClick(newsArticleUrl: String)
}