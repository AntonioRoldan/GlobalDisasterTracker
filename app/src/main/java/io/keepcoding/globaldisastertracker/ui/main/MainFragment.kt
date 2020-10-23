package io.keepcoding.globaldisastertracker.ui.main

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import io.keepcoding.globaldisastertracker.R
import io.keepcoding.globaldisastertracker.repository.local.DisasterEventsRoomDatabase
import io.keepcoding.globaldisastertracker.repository.local.LocalHelperImpl
import io.keepcoding.globaldisastertracker.repository.remote.ApiHelperImpl
import io.keepcoding.globaldisastertracker.repository.remote.RemoteDataManager
import io.keepcoding.globaldisastertracker.utils.CustomViewModelFactory
import io.keepcoding.globaldisastertracker.utils.Status
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.try_again.*
import kotlin.IllegalArgumentException

private const val ARG_FROM_SERVER = "FROM_SERVER"

/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var fromServer: Boolean = false

    private var events: List<EventItemViewModel?>? = mutableListOf(null)

    private var mainInteractionListener: MainInteractionListener? = null // We will pass this to the recycler view adapter

    private val eventsAdapter: EventsAdapter by lazy {
        lateinit var adapter: EventsAdapter
        if(fromServer){
            context?.let { context ->
                adapter = EventsAdapter(context) {
                    mainInteractionListener?.onItemClickFromServer(it)
                }
            }
        } else {
            context?.let { context ->
                adapter = EventsAdapter(context) {
                    mainInteractionListener?.onItemClickFromLocal(it)
                }
            }
        }
        adapter.eventItems = events
        adapter
    }

    private val viewModel: MainFragmentViewModel by lazy {
        val factory = CustomViewModelFactory(requireActivity().application,
            ApiHelperImpl(RemoteDataManager().bingSearchApi, RemoteDataManager().eonetApi),
            LocalHelperImpl(DisasterEventsRoomDatabase.getInstance(requireActivity()))
        )
        ViewModelProvider(this, factory).get(MainFragmentViewModel::class.java)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is MainInteractionListener){
            mainInteractionListener = context
        } else {
            throw IllegalArgumentException("Context does not implement interface ${MainInteractionListener::class.java.canonicalName}")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            fromServer = it.getBoolean(ARG_FROM_SERVER)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUI()
        setUpListeners()
        setUpObservers()
    }

    private fun setUpListeners(){
        buttonRetry.setOnClickListener {
            retry.visibility = View.INVISIBLE
            loadingView.visibility = View.VISIBLE
            fetchData()
        }
    }

    private fun setUpUI(){
        list.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    private fun fetchData(){
        if(fromServer){
            viewModel.fetchApiEvents()
        } else{
            viewModel.fetchLocalEvents()
        }
    }

    private fun setUpObservers(){
        fetchData()
        viewModel.getEvents().observe(viewLifecycleOwner, Observer {
            when(it.status){
                Status.SUCCESS -> {
                    events = it.data
                    Log.v("EVENTS", "$events")
                    loadingView.visibility = View.INVISIBLE
                    list.visibility = View.VISIBLE
                    list.adapter = eventsAdapter
                }
                Status.LOADING -> {
                    loadingView.visibility = View.VISIBLE
                    retry.visibility = View.INVISIBLE
                    list.visibility = View.INVISIBLE
                } // We make recycler view invisible and spinning wheel visible
                Status.ERROR -> {
                    loadingView.visibility = View.INVISIBLE
                    retry.visibility = View.VISIBLE
                    list.visibility = View.INVISIBLE
                    Toast.makeText(requireActivity().application, it.message, Toast.LENGTH_LONG)
                }
            }
        })
    }

    fun updateList(){
        eventsAdapter.notifyDataSetChanged()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MainFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(fromServer: Boolean) =
            MainFragment().apply {
                arguments = Bundle().apply {
                    putBoolean(ARG_FROM_SERVER, fromServer)
                }
            }
    }
}