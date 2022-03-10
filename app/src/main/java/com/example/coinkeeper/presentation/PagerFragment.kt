package com.example.coinkeeper.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.coinkeeper.R




class PagerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_page, container, false)
    }

    @SuppressLint("Recycle")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val arrayTitle = resources.getStringArray(R.array.title)
        val arrayContent = resources.getStringArray(R.array.content)
        val arrayIco = resources.obtainTypedArray(R.array.icons)
        val tvTitle: TextView = view.findViewById(R.id.fp_tv_title)
        val tvContent: TextView = view.findViewById(R.id.fp_tv_content)
        val ivIcon: ImageView = view.findViewById(R.id.icon)

        arguments?.takeIf { it.containsKey(ARG_OBJECT) }?.apply {
            val position = getInt(ARG_OBJECT)
            tvTitle.text = arrayTitle[position-1]
            tvContent.text = arrayContent[position-1]
            ivIcon.setImageResource(arrayIco.getResourceId(position-1,0))
        }
    }

    companion object{

        const val ARG_OBJECT = "object"
    }
}