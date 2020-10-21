package io.keepcoding.globaldisastertracker.ui.detail

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import io.keepcoding.globaldisastertracker.R
import io.keepcoding.globaldisastertracker.domain.EventsItem
import io.keepcoding.globaldisastertracker.repository.local.DisasterEventsRoomDatabase
import io.keepcoding.globaldisastertracker.repository.local.LocalHelperImpl
import io.keepcoding.globaldisastertracker.repository.remote.ApiHelperImpl
import io.keepcoding.globaldisastertracker.repository.remote.RemoteDataManager
import io.keepcoding.globaldisastertracker.utils.CustomViewModelFactory
import io.keepcoding.globaldisastertracker.utils.Status
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    companion object {
        const val TAG = "DetailActivity"
        const val LOCAL = "LOCAL"
    }

    private val viewModel: DetailViewModel by lazy {
        val factory = CustomViewModelFactory(application,
            ApiHelperImpl(RemoteDataManager().bingSearchApi, RemoteDataManager().eonetApi),
            LocalHelperImpl(DisasterEventsRoomDatabase.getInstance(applicationContext))
        )
        ViewModelProvider(this, factory).get(DetailViewModel::class.java)
    }

    private lateinit var eventsItem: EventsItem

    private var imageItems: List<ImageItemViewModel?>? = mutableListOf(null)

    private var newsItems: List<NewsItemViewModel?>? = mutableListOf(null)

    private var loading: Boolean = false

    private var eventId: String? = null

    //private lateinit var eventsAdapter: EventsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(findViewById(R.id.toolbar))
        setUpUI()
        setUpObservers()
        setUpListeners()
    }

    private fun setUpListeners(){
        fab.setOnClickListener { view ->
            intent?.let {intent ->
                if(intent.getStringExtra("SERVER_OR_LOCAL") == LOCAL){
                    eventId?.let { eventId ->
                        viewModel.deleteEvent(eventId)
                    }
                } else {
                    viewModel.saveEvent(eventsItem)
                }
            }
        }
    }

    private fun setUpUI(){

    }

    private fun observe(fromServer: Boolean) {
        eventsItem?.title?.let { title ->
            if(fromServer){
                viewModel.fetchApiNews(title)
                viewModel.fetchApiImages(title)
            } else {
                eventId?.let {id ->
                    viewModel.loadImagesFromLocal(id)
                    viewModel.loadNewsFromLocal(id)
                }
            }
            viewModel.getImages().observe(this, Observer { images ->
                when (images.status) {
                    Status.SUCCESS -> imageItems = images.data
                    Status.LOADING -> loading = true
                    Status.ERROR -> Toast.makeText(this, images.message, Toast.LENGTH_LONG).show()
                }
            })
            viewModel.getNews().observe(this, Observer { news ->
                when (news.status) {
                    Status.SUCCESS -> newsItems = news.data
                    Status.LOADING -> loading = true
                    Status.ERROR -> Toast.makeText(this, news.message, Toast.LENGTH_LONG).show()
                }
            })
            viewModel.getSnackBar().observe(this, Observer { snackBar ->
                when(snackBar.status) {
                    Status.SUCCESS -> Toast.makeText(this, snackBar.data, Toast.LENGTH_LONG).show()
                    Status.ERROR -> Toast.makeText(this, snackBar.message, Toast.LENGTH_LONG).show()
                }
            })
        }
    }

    private fun setUpObservers(){
        intent?.let {
            eventId = it.getStringExtra("EVENT_ID")
            observe(it.getStringExtra("SERVER_OR_LOCAL") == LOCAL)
        }
    }

}