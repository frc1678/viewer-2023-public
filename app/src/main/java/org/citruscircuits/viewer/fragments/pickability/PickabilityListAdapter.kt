package org.citruscircuits.viewer.fragments.pickability

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import org.citruscircuits.viewer.R
import org.citruscircuits.viewer.constants.Constants
import org.citruscircuits.viewer.databinding.PickabilityCellBinding
import java.lang.Float.parseFloat

/**
 * Adapter for the pickability list.
 */
class PickabilityListAdapter(
    context: Context,
    var items: Map<String, String>
) : BaseAdapter() {
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
        val pickability = items[e]!!

        val rowView = PickabilityCellBinding.inflate(inflater)
        rowView.tvPlacement.text = (i + 1).toString()
        rowView.tvTeamNumber.text = e
        rowView.tvPickability.text =
            (if (pickability != Constants.NULL_CHARACTER) parseFloat(("%.1f").format(pickability.toFloat())).toString() else pickability)
        return rowView.root


    }
}
