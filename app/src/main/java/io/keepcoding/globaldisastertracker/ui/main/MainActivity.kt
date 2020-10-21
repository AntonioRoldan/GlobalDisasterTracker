package io.keepcoding.globaldisastertracker.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import io.keepcoding.globaldisastertracker.R
import io.keepcoding.globaldisastertracker.repository.local.DisasterEventsRoomDatabase
import io.keepcoding.globaldisastertracker.repository.local.LocalHelperImpl
import io.keepcoding.globaldisastertracker.repository.remote.ApiHelperImpl
import io.keepcoding.globaldisastertracker.repository.remote.RemoteDataManager
import io.keepcoding.globaldisastertracker.ui.detail.DetailActivity
import io.keepcoding.globaldisastertracker.utils.CustomViewModelFactory
import io.keepcoding.globaldisastertracker.utils.REQUEST_CODE

class MainActivity : AppCompatActivity(), CallbackItemClick {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_CODE){

        }
    }

    override fun onItemClickFromServer(eventItemViewModel: EventItemViewModel) {
        // If we are coming from the server events list we set FROM_SERVER to true
        var bundle = Bundle()
        bundle.putParcelable("EVENT_ITEM", eventItemViewModel)
        bundle.putBoolean("FROM_SERVER", true)
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("bundle", bundle)
        startActivityForResult(intent, REQUEST_CODE)
    }

    override fun onItemClickFromLocal(eventItemViewModel: EventItemViewModel) {
        // If we are coming from the local events list we set FROM_SERVER to false
        var bundle = Bundle()
        bundle.putParcelable("EVENT_ITEM", eventItemViewModel)
        bundle.putBoolean("FROM_SERVER", false)
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("bundle", bundle)
        startActivityForResult(intent, REQUEST_CODE)
    }

}