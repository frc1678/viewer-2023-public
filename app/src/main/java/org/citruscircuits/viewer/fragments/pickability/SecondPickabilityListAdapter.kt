package org.citruscircuits.viewer.fragments.pickability

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import org.citruscircuits.viewer.R
import org.citruscircuits.viewer.constants.Constants
import kotlinx.android.synthetic.main.pickability_cell.view.*
import java.lang.Float.parseFloat

class SecondPickabilityListAdapter(private val context: Context, var items: Map<String, Float?>) :
    BaseAdapter() {
    private val inflater = LayoutInflater.from(context)
    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(i: Int): String {
        return items.keys.elementAt(i)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(i: Int, view: View?, parent: ViewGroup?): View {
        val e = getItem(i)
        val pickability = if (items[e] == (-1000).toFloat()) {
            Constants.NULL_CHARACTER
        } else {
            items[e]!!.toString()
        }
        val rowView = inflater.inflate(R.layout.pickability_cell, parent, false)
        rowView.tv_placement.text = (i + 1).toString()
        rowView.tv_team_number.text = e
        rowView.tv_pickability.text =
            (if (pickability != Constants.NULL_CHARACTER) parseFloat(("%.1f").format(pickability.toFloat())).toString() else pickability)
        return rowView
    }
}