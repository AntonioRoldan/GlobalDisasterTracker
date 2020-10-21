package io.keepcoding.globaldisastertracker.ui.detail

import android.os.Bundle
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import io.keepcoding.globaldisastertracker.R
import io.keepcoding.globaldisastertracker.domain.EventsItem
import io.keepcoding.globaldisastertracker.utils.Status
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    companion object {
        const val TAG = "DetailActivity"
        const val FROM_LOCAL = "LOCAL"
    }

    private lateinit var viewModel: DetailViewModel

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
        setUpViewModel()
        setUpObservers()
        setUpListeners()
    }

    private fun setUpListeners(){
        fab.setOnClickListener { view ->
            intent?.let {intent ->
                if(intent.getStringExtra("SERVER_OR_LOCAL") == FROM_LOCAL){
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
            observe(it.getStringExtra("SERVER_OR_LOCAL") == FROM_LOCAL)
        }
    }

    private fun setUpViewModel(){

    }
}