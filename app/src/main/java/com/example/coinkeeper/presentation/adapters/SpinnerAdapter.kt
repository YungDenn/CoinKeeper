package com.example.coinkeeper.presentation.adapters


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.coinkeeper.domain.entity.CategoryOperation
import android.widget.ImageView


import android.widget.TextView
import com.example.coinkeeper.R

class SpinnerAdapter(val context: Context, var list: List<CategoryOperation>) :
    BaseAdapter() {

    private val mInflater: LayoutInflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val vh: ItemRowHolder
        if (convertView == null) {
            view = mInflater.inflate(R.layout.spinner_item, parent, false)
            vh = ItemRowHolder(view)
            view.tag = vh
        } else {
            view = convertView
            vh = view.tag as ItemRowHolder
        }

        // setting adapter item height programatically.

//        val params = view.layoutParams
//        params.height = 60
//        view.layoutParams = params

        vh.itemLabel.text = list[position].name
        vh.itemImage.setBackgroundResource( list[position].image_id)
        return view
    }

    override fun getItem(position: Int): Any? {

        return null

    }

    override fun getItemId(position: Int): Long {

        return 0

    }

    override fun getCount(): Int {
        return list.size
    }

    private class ItemRowHolder(row: View?) {

        val itemLabel: TextView = row?.findViewById(R.id.tvNameSpinner) as TextView
        val itemImage: ImageView = row?.findViewById(R.id.ivLogoSpinner) as ImageView

    }


}