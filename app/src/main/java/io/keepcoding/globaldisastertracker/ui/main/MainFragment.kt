package io.keepcoding.globaldisastertracker.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import io.keepcoding.globaldisastertracker.R
import io.keepcoding.globaldisastertracker.repository.local.DisasterEventsRoomDatabase
import io.keepcoding.globaldisastertracker.repository.local.LocalHelperImpl
import io.keepcoding.globaldisastertracker.repository.remote.ApiHelperImpl
import io.keepcoding.globaldisastertracker.repository.remote.RemoteDataManager
import io.keepcoding.globaldisastertracker.ui.detail.DetailActivity
import io.keepcoding.globaldisastertracker.utils.CustomViewModelFactory
import io.keepcoding.globaldisastertracker.utils.REQUEST_CODE
import io.keepcoding.globaldisastertracker.utils.Status

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
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

    private var loading: Boolean = false

    private val viewModel: MainFragmentViewModel by lazy {
        val factory = CustomViewModelFactory(requireActivity().application,
            ApiHelperImpl(RemoteDataManager().bingSearchApi, RemoteDataManager().eonetApi),
            LocalHelperImpl(DisasterEventsRoomDatabase.getInstance(requireActivity().applicationContext))
        )
        ViewModelProvider(this, factory).get(MainFragmentViewModel::class.java)
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
        setUpObservers()
    }

    private fun setUpObservers(){
        if(fromServer){
            viewModel.fetchApiEvents()
        } else{
            viewModel.fetchLocalEvents()
        }
        viewModel.getEvents().observe(viewLifecycleOwner, Observer {
            when(it.status){
                Status.SUCCESS -> events = it.data
                Status.LOADING -> loading = true
                Status.ERROR -> Toast.makeText(requireActivity().application, it.message, Toast.LENGTH_LONG)
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