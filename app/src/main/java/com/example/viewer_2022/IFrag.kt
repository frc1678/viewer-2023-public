package com.example.viewer_2022

import android.util.Log
import android.widget.BaseAdapter
import androidx.fragment.app.Fragment

abstract class IFrag : Fragment(){
    var adapter: BaseAdapter? = null
    fun updateListView(): Unit {
            Log.e("help", "updating list view")
            if(adapter != null){
                adapter!!.notifyDataSetChanged()
                Log.e("help", "done updating list view")

            } else {
                Log.e("help", "adapter is null")
            }
    }
}