package io.keepcoding.globaldisastertracker.ui.detail

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import io.keepcoding.globaldisastertracker.ui.main.EventItemViewModel

class ViewPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity){

    private var fromServer: Boolean? = false

    private var eventItem: EventItemViewModel? = null

    override fun getItemCount(): Int {
        return 2
    }

    fun setFragmentArguments(eventItem: EventItemViewModel?, fromServer: Boolean?){
        this.fromServer = fromServer
        this.eventItem = eventItem
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> DetailFragment.newInstance(eventItem = eventItem!!, fromServer = fromServer!!, isNewsFragment = true)
            else -> DetailFragment.newInstance(eventItem = eventItem!!, fromServer = fromServer!!, isNewsFragment = false)
        }
    }

}