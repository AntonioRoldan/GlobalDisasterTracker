package io.keepcoding.globaldisastertracker.ui.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.keepcoding.globaldisastertracker.R
import io.keepcoding.globaldisastertracker.repository.local.DisasterEventsRoomDatabase
import io.keepcoding.globaldisastertracker.repository.local.LocalHelperImpl
import io.keepcoding.globaldisastertracker.repository.remote.ApiHelperImpl
import io.keepcoding.globaldisastertracker.repository.remote.RemoteDataManager
import io.keepcoding.globaldisastertracker.ui.main.EventItemViewModel
import io.keepcoding.globaldisastertracker.utils.CustomViewModelFactory
import io.keepcoding.globaldisastertracker.utils.Status
import kotlinx.android.synthetic.main.fragment_detail.list
import kotlinx.android.synthetic.main.fragment_detail.loadingView
import kotlinx.android.synthetic.main.fragment_detail.retry
import kotlinx.android.synthetic.main.try_again.*

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
class DetailFragment : Fragment() {
    // Fragment parameters
    private val fromServer: Boolean by lazy {
        var server = false
        arguments?.let {
            server = it.getBoolean(ARG_FROM_SERVER)
        }
        server
    }
    private val eventItem: EventItemViewModel by lazy {
        lateinit var eventViewModel: EventItemViewModel
        arguments?.let {
            eventViewModel = it.getParcelable(ARG_EVENT_ITEM)!!
        }
        eventViewModel
    }
    private var isNewsFragment: Boolean = false

    private var imageItems: List<ImageItemViewModel?>? = mutableListOf(null)

    private var newsItems: List<NewsItemViewModel?>? = mutableListOf(null)

    private var detailsAdapter: DetailAdapter? = null


    private val viewModel: DetailFragmentViewModel by lazy {
        val factory = CustomViewModelFactory(requireActivity().application,
            ApiHelperImpl(RemoteDataManager().bingSearchApi, RemoteDataManager().eonetApi),
            LocalHelperImpl(DisasterEventsRoomDatabase.getInstance(requireActivity()))
        )
        ViewModelProvider(this, factory).get(DetailFragmentViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            isNewsFragment = it.getBoolean(ARG_IS_NEWS_FRAGMENT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpListeners()
        setUpRecyclerView()
        setUpObservers()
    }

    fun setFABClickListener(fab: FloatingActionButton?){
        if(fromServer){
            fab?.setOnClickListener {
                viewModel.saveEvent(eventViewModel = eventItem)
            }
        } else {
            fab?.setOnClickListener {
                viewModel.deleteEvent(eventItem.id!!)
            }
        }
    }

    private fun setUpListeners(){
        buttonRetry.setOnClickListener {
            fetchData(eventItem)
        }
    }

    private fun setAdapter(){
        context?.let { context ->
            detailsAdapter = DetailAdapter(context, requireActivity() as DetailActivity)
            detailsAdapter?.setData(newsItems, imageItems, isNewsFragment)
        }
    }

    private fun setUpRecyclerView(){
        if(isNewsFragment){ // Display linear layout with news
            list.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        } else { // Display grid layout with images
            list.layoutManager = GridLayoutManager(context, 3)
            list.addItemDecoration(DividerItemDecoration(context, GridLayoutManager.VERTICAL))
        }
        setAdapter()
    }

    private fun fetchData(event: EventItemViewModel){
        if(fromServer){
            event.title?.let { title ->
                if(isNewsFragment) viewModel.fetchApiNews(title)
                else viewModel.fetchApiImages(title)
            }
        } else {
            event.id?.let {id ->
                if(isNewsFragment) viewModel.loadNewsFromLocal(id)
                else viewModel.loadImagesFromLocal(id)
            }
        }
    }

    private fun observeImages(){
        viewModel.getImages().observe(viewLifecycleOwner, Observer { images ->
            when (images.status) {
                Status.SUCCESS -> {
                    imageItems = images.data
                    loadingView.visibility = View.GONE
                    retry.visibility = View.GONE
                    list.visibility = View.VISIBLE
                    setAdapter()
                    list.adapter = detailsAdapter
                    viewModel.fetchApiNews(eventItem.title!!)
                }
                Status.LOADING -> {
                    loadingView.visibility = View.VISIBLE
                    retry.visibility = View.INVISIBLE
                    list.visibility = View.INVISIBLE
                }
                Status.ERROR -> {
                    retry.visibility = View.VISIBLE
                    loadingView.visibility = View.INVISIBLE
                    list.visibility = View.INVISIBLE
                }
            }
        })
    }

    private fun observeNews(){
        viewModel.getNews().observe(viewLifecycleOwner, Observer { news ->
            when (news.status) {
                Status.SUCCESS -> {
                    newsItems = news.data
                    loadingView.visibility = View.GONE
                    retry.visibility = View.GONE
                    list.visibility = View.VISIBLE
                    setAdapter()
                    list.adapter = detailsAdapter
                    viewModel.fetchApiImages(eventItem.title!!) // We fetch the images too, in order to store them, if user has not switched to images tab
                }
                Status.LOADING -> {
                    retry.visibility = View.INVISIBLE
                    loadingView.visibility = View.VISIBLE
                    list.visibility = View.INVISIBLE
                }
                Status.ERROR -> {
                    retry.visibility = View.VISIBLE
                    loadingView.visibility = View.INVISIBLE
                    list.visibility = View.INVISIBLE
                }
            }
        })
    }
    private fun setUpObservers(){
        fetchData(eventItem)
        if(isNewsFragment){
            observeNews()
        } else {
            observeImages()
        }
        viewModel.getSnackBar().observe(viewLifecycleOwner, Observer { snackBar ->
            when(snackBar.status) {
                Status.SUCCESS -> Toast.makeText(requireActivity().application, snackBar.data, Toast.LENGTH_LONG).show()
                else -> Toast.makeText(requireActivity().application, snackBar.message, Toast.LENGTH_LONG).show()
            }
        })
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
        fun newInstance(fromServer: Boolean, eventItem : EventItemViewModel, isNewsFragment: Boolean) =
            DetailFragment().apply {
                arguments = Bundle().apply {
                    putBoolean(ARG_FROM_SERVER, fromServer)
                    putBoolean(ARG_IS_NEWS_FRAGMENT, isNewsFragment)
                    putParcelable(ARG_EVENT_ITEM, eventItem)

                }
            }
    }
}