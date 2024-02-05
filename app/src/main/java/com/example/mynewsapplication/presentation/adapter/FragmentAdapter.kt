package com.example.mynewsapplication.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mynewsapplication.presentation.BusinessFragment
import com.example.mynewsapplication.presentation.EntertainmentFragment
import com.example.mynewsapplication.presentation.GeneralFragment
import com.example.mynewsapplication.presentation.HealthFragment
import com.example.mynewsapplication.presentation.ScienceFragment
import com.example.mynewsapplication.presentation.SportsFragment
import com.example.mynewsapplication.presentation.TechFragment
import com.example.mynewsapplication.utils.Constants.TOTAL_NEWS_TAB


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