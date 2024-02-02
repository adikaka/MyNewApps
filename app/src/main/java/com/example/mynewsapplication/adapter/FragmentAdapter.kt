package com.example.mynewsapplication.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mynewsapplication.utils.Constants.TOTAL_NEWS_TAB
import com.example.newsapp.fragmentClasses.BusinessFragment
import com.example.newsapp.fragmentClasses.EntertainmentFragment
import com.example.newsapp.fragmentClasses.GeneralFragment
import com.example.newsapp.fragmentClasses.HealthFragment
import com.example.newsapp.fragmentClasses.ScienceFragment
import com.example.newsapp.fragmentClasses.SportsFragment
import com.example.newsapp.fragmentClasses.TechFragment

class FragmentAdapter(fm: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fm, lifecycle){

    override fun getItemCount(): Int = TOTAL_NEWS_TAB

    override fun createFragment(position: Int): Fragment {

        when (position) {
            0 -> {
                return GeneralFragment()
            }
            1 -> {
                return BusinessFragment()
            }
            2 -> {
                return EntertainmentFragment()
            }
            3 -> {
                return ScienceFragment()
            }
            4 -> {
                return SportsFragment()
            }
            5 -> {
                return TechFragment()
            }
            6 -> {
                return HealthFragment()
            }

            else -> return BusinessFragment()

        }
    }
}