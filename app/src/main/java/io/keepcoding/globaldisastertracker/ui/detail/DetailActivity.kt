package io.keepcoding.globaldisastertracker.ui.detail

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
import com.google.android.material.tabs.TabLayoutMediator
import io.keepcoding.globaldisastertracker.R
import io.keepcoding.globaldisastertracker.ui.main.EventItemViewModel
import kotlinx.android.synthetic.main.activity_detail.*


class DetailActivity : AppCompatActivity(), DetailInteractionListener {

    companion object {
        const val TAG = "DetailActivity"
        const val LOCAL = "LOCAL"
        const val ARG_EVENT_ITEM = "EVENT_ITEM"
        const val ARG_FROM_SERVER ="FROM_SERVER"
    }

    private var eventItem: EventItemViewModel? = null

    private var fromServer: Boolean? = false

    private lateinit var viewPagerAdapter : ViewPagerAdapter

    //private lateinit var mainTabAdapter: MainTabAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val bundle: Bundle? = intent.getBundleExtra("bundle")
        eventItem = bundle?.getParcelable(ARG_EVENT_ITEM)
        fromServer = bundle?.getBoolean(ARG_FROM_SERVER)
        viewPagerAdapter = ViewPagerAdapter(this)
        viewPagerAdapter.setFragmentArguments(eventItem, fromServer)
        viewPagerAdapter.setFAB(fab)
        viewPager.adapter = viewPagerAdapter
        TabLayoutMediator(tabLayout, viewPager) {tab, position ->
            when(position){
                0 -> tab.text = "News"
                1 -> tab.text = "Images"
            }
        }.attach()

    }

    override fun onImageItemClick(imageUrl: String) {
        Log.v("suup", "yo")
    }

    override fun onNewsItemClick(newsArticleUrl: String) {
        Log.v("suup", "yo")
    }
}