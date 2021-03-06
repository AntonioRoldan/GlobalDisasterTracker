package io.keepcoding.globaldisastertracker.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    private lateinit var eonetFragment: MainFragment
    lateinit var localFragment: MainFragment // We make this public so we can call notify listeners from main activity

    override fun getItemCount(): Int {
        return 2
    }

    fun localFragmentIsInitialized(): Boolean {
        return ::localFragment.isInitialized
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> {
                eonetFragment = MainFragment.newInstance(true)
                eonetFragment
            }
            else -> {
                localFragment = MainFragment.newInstance(false)
                localFragment
            }
        }
    }


}