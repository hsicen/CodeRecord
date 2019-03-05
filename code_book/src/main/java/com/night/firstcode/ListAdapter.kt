package com.night.firstcode

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

/**
 * <p>作者：Night  2019/3/4 20:40
 * <p>邮箱：codinghuang@163.com
 * <p>作用：
 * <p>描述：ListView Adapter
 */
class ListAdapter(context: Context, val data: MutableList<String>)
    : BaseAdapter() {

    private val mInflater by lazy {
        LayoutInflater.from(context)
    }

    override fun getView(position: Int, convertView: View,
                         parent: ViewGroup?): View {

        var viewHolder: ViewHolder? = null

        /*if (convertView == null) {

            viewHolder = ViewHolder()
            convertView = mInflater.inflate(R.layout.activity_second, parent)

            viewHolder.img = convertView.findViewById()
            convertView.tag = viewHolder
        } else {
            viewHolder = convertView.tag as ViewHolder
        }*/

        return convertView
    }

    override fun getItem(position: Int): Any = data[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getCount(): Int = data.size

    //View holder
    class ViewHolder {

        var img: ImageView? = null
        var title: TextView? = null
    }
}