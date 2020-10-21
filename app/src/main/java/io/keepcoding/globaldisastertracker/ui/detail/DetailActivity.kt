package io.keepcoding.globaldisastertracker.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import io.keepcoding.globaldisastertracker.R
import io.keepcoding.globaldisastertracker.ui.main.EventItemViewModel

class DetailActivity : AppCompatActivity(), CallbackItemClick {

    companion object {
        const val TAG = "DetailActivity"
        const val LOCAL = "LOCAL"
        const val ARG_EVENT_ITEM = "EVENT_ITEM"
        const val ARG_FROM_SERVER ="FROM_SERVER"
    }

    private var eventItem: EventItemViewModel? = null

    private var fromServer: Boolean = false

    //private lateinit var mainTabAdapter: MainTabAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(findViewById(R.id.toolbar))
        val bundle: Bundle = intent.getBundleExtra("bundle")
        eventItem = bundle.getParcelable(ARG_EVENT_ITEM)
        fromServer = bundle.getBoolean(ARG_FROM_SERVER)

    }

    override fun onImageItemClick(imageUrl: String) {
        TODO("Not yet implemented")
    }

    override fun onNewsItemClick(newsArticleUrl: String) {
        TODO("Not yet implemented")
    }
}