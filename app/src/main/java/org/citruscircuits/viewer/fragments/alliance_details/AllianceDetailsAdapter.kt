package org.citruscircuits.viewer.fragments.alliance_details

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.fragment.app.FragmentActivity
import kotlinx.android.synthetic.main.alliance_details_cell.view.*
import org.citruscircuits.viewer.R

class AllianceDetailsAdapter (
    context: FragmentActivity
): BaseAdapter() {

    private val inflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return 8
    }

    override fun getItem(p0: Int): Any {
        return ""
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val rowView = inflater.inflate(R.layout.alliance_details_cell, parent, false)

        rowView.alliance_details_alliance_num.text =

        return rowView
    }

}