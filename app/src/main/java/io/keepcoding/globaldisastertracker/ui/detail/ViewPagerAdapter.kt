package io.keepcoding.globaldisastertracker.ui.detail

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.keepcoding.globaldisastertracker.ui.main.EventItemViewModel

class ViewPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity){

    private var fromServer: Boolean? = false

    private var eventItem: EventItemViewModel? = null

    private var fabButton: FloatingActionButton? = null

    private lateinit var newsFragment: DetailFragment

    private lateinit var imagesFragment: DetailFragment

    override fun getItemCount(): Int {
        return 2
    }

    fun setFAB(fabButton: FloatingActionButton?){
        this.fabButton = fabButton
    }

    fun setFragmentArguments(eventItem: EventItemViewModel?, fromServer: Boolean?){
        this.fromServer = fromServer
        this.eventItem = eventItem
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> {
                newsFragment = DetailFragment.newInstance(eventItem = eventItem!!, fromServer = fromServer!!, isNewsFragment = true)
                newsFragment.setFABClickListener(fabButton)
                newsFragment
            }
            else -> {
                imagesFragment = DetailFragment.newInstance(eventItem = eventItem!!, fromServer = fromServer!!, isNewsFragment = false)
                imagesFragment.setFABClickListener(fabButton)
                imagesFragment
            }
        }
    }

}