package com.example.coinkeeper.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.coinkeeper.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class Info_Fragment : Fragment() {

    private lateinit var adapter: PagerViewAdapter
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_info_, container, false)
        setupAdapter(view)
        setupTabLayout(view)
        return view
    }

    private fun setupAdapter(view: View){
        adapter = PagerViewAdapter(requireActivity())
        viewPager = view.findViewById(R.id.pager)
        viewPager.adapter = adapter
    }

    private fun setupTabLayout(view: View){
        val tabNames = resources.getStringArray(R.array.tab_names)
        tabLayout = view.findViewById(R.id.tab_layout)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabNames[position]

        }.attach()
    }


}