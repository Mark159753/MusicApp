package com.example.musicapp.ui.home.adapters.vp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.example.musicapp.R
import com.example.musicapp.model.homeTabs.HomeTabs

class VpAdapterTabs
    (private val context: Context): PagerAdapter(){

    private var dataList:List<HomeTabs> = emptyList()

    private var listener: ItemSelectedListener? = null

    fun setItemSelectedListener(listener: ItemSelectedListener){
        this.listener = listener
    }

    fun setDataList(list: List<HomeTabs>){
        this.dataList = list
        notifyDataSetChanged()
    }

//    override fun getItemPosition(`object`: Any): Int {
//        return POSITION_NONE
//    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return dataList.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val v = LayoutInflater.from(context).inflate(R.layout.home_tab_item, container, false)
        val tabContainer = v.findViewById<RelativeLayout>(R.id.home_tab_container)
        val title = v.findViewById<TextView>(R.id.home_tab_title)
        tabContainer.setBackgroundColor(dataList[position].tabColor)
        title.text = dataList[position].tabName

        v.transitionName = "${context.getString(R.string.transition_tab_name)}${dataList[position].id}"

        v.setOnClickListener {
            listener?.onItemSelected(position, it)
        }

        container.addView(v)
        return v
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}