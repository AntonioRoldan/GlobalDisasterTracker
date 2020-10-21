package io.keepcoding.globaldisastertracker.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.keepcoding.globaldisastertracker.R
import io.keepcoding.globaldisastertracker.ui.main.EventItemViewModel

class DetailActivity : AppCompatActivity(), CallbackItemClick {

    companion object {
        const val TAG = "DetailActivity"
        const val LOCAL = "LOCAL"
        const val ARG_EVENT_ITEM = "EVENT_ITEM"
    }

    private lateinit var eventItem: EventItemViewModel

    //private lateinit var mainTabAdapter: MainTabAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(findViewById(R.id.toolbar))
        intent?.let { eventItem = it.getParcelableExtra(ARG_EVENT_ITEM)}
    }

    override fun onImageItemClick(imageUrl: String) {
        TODO("Not yet implemented")
    }

    override fun onNewsItemClick(newsArticleUrl: String) {
        TODO("Not yet implemented")
    }
}