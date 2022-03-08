package com.example.coinkeeper.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.coinkeeper.R
import com.example.coinkeeper.presentation.PagerFragment.Companion.ARG_OBJECT

class PagerViewAdapter(fragment: FragmentActivity) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 5

    override fun createFragment(position: Int): Fragment {
        val fragment = PagerFragment()
        fragment.arguments = Bundle().apply {
            putInt(ARG_OBJECT, position + 1)

        }
        return fragment
    }
}