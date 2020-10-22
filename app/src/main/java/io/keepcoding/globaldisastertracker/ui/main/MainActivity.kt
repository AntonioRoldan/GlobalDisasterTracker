package io.keepcoding.globaldisastertracker.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import io.keepcoding.globaldisastertracker.R
import io.keepcoding.globaldisastertracker.ui.detail.DetailActivity
import io.keepcoding.globaldisastertracker.utils.REQUEST_CODE
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainInteractionListener {

    private var viewPagerAdapter: ViewPagerAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewPagerAdapter = ViewPagerAdapter(this)
        viewPager.adapter = viewPagerAdapter
        TabLayoutMediator(tabLayout, viewPager) {tab, position ->
            when(position){
                0 -> tab.text = "EONET"
                1 -> tab.text = "Local"
            }
        }.attach()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_CODE){
            viewPagerAdapter?.localFragment?.updateList()
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