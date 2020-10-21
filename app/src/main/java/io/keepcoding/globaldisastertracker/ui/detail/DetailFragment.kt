package io.keepcoding.globaldisastertracker.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import io.keepcoding.globaldisastertracker.R
import io.keepcoding.globaldisastertracker.domain.EventsItem
import io.keepcoding.globaldisastertracker.repository.local.DisasterEventsRoomDatabase
import io.keepcoding.globaldisastertracker.repository.local.LocalHelperImpl
import io.keepcoding.globaldisastertracker.repository.remote.ApiHelperImpl
import io.keepcoding.globaldisastertracker.repository.remote.RemoteDataManager
import io.keepcoding.globaldisastertracker.ui.main.EventItemViewModel
import io.keepcoding.globaldisastertracker.utils.CustomViewModelFactory
import io.keepcoding.globaldisastertracker.utils.Status
import kotlinx.android.synthetic.main.activity_detail.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_FROM_SERVER = "FROM_SERVER"
private const val ARG_EVENT_ITEM = "EVENT_ITEM"
private const val ARG_IS_NEWS_FRAGMENT ="IS_NEWS_FRAGMENT"


/**
 * A simple [Fragment] subclass.
 * Use the [ListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListFragment : Fragment() {
    // Fragment parameters
    private var fromServer: Boolean = false
    private var eventItem: EventItemViewModel? = null
    private var isNewsFragment: Boolean = false

    private var imageItems: List<ImageItemViewModel?>? = mutableListOf(null)

    private var newsItems: List<NewsItemViewModel?>? = mutableListOf(null)

    private var loading: Boolean = false

    private val viewModel: DetailFragmentViewModel by lazy {
        val factory = CustomViewModelFactory(requireActivity().application,
            ApiHelperImpl(RemoteDataManager().bingSearchApi, RemoteDataManager().eonetApi),
            LocalHelperImpl(DisasterEventsRoomDatabase.getInstance(requireActivity().applicationContext))
        )
        ViewModelProvider(this, factory).get(DetailFragmentViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            fromServer = it.getBoolean(ARG_FROM_SERVER)
            eventItem = it.getParcelable(ARG_EVENT_ITEM)
            isNewsFragment = it.getBoolean(ARG_IS_NEWS_FRAGMENT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpObservers()
        setUpListeners()
    }

    private fun setUpRecyclerViewAdapter(){
        if(isNewsFragment){ // Display linear layout with news

        } else { // Display grid layout with images

        }
    }
    private fun setUpListeners(){
        fab.setOnClickListener { view ->
            if(fromServer){
                eventItem?.let {  viewModel.saveEvent(it) }
            } else {
                eventItem?.id?.let { eventId -> viewModel.deleteEvent(eventId) }
            }
        }
    }

    private fun setUpObservers(){
        eventItem?.let { event ->
            if(fromServer){
                event.title?.let { title ->
                    viewModel.fetchApiNews(title)
                    viewModel.fetchApiImages(title)
                }
            } else {
                event.id?.let {id ->
                    viewModel.loadImagesFromLocal(id)
                    viewModel.loadNewsFromLocal(id)
                }
            }
            viewModel.getImages().observe(viewLifecycleOwner, Observer { images ->
                when (images.status) {
                    Status.SUCCESS -> imageItems = images.data
                    Status.LOADING -> loading = true
                    Status.ERROR -> Toast.makeText(requireActivity().application, images.message, Toast.LENGTH_LONG).show()
                }
            })
            viewModel.getNews().observe(viewLifecycleOwner, Observer { news ->
                when (news.status) {
                    Status.SUCCESS -> newsItems = news.data
                    Status.LOADING -> loading = true
                    Status.ERROR -> Toast.makeText(requireActivity().application, news.message, Toast.LENGTH_LONG).show()
                }
            })
            viewModel.getSnackBar().observe(viewLifecycleOwner, Observer { snackBar ->
                when(snackBar.status) {
                    Status.SUCCESS -> Toast.makeText(requireActivity().application, snackBar.data, Toast.LENGTH_LONG).show()
                    Status.ERROR -> Toast.makeText(requireActivity().application, snackBar.message, Toast.LENGTH_LONG).show()
                }
            })
        }
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(fromServer: String, eventItem : EventItemViewModel) =
            ListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_FROM_SERVER, fromServer)
                    putParcelable(ARG_EVENT_ITEM, eventItem)
                }
            }
    }
}