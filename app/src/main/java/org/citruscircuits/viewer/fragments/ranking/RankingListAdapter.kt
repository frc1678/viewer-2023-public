/*
* RankingListAdapter.kt
* viewer
*
* Created on 2/8/2020
* Copyright 2020 Citrus Circuits. All rights reserved.
*/

package org.citruscircuits.viewer.fragments.ranking

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import org.citruscircuits.viewer.R
import org.citruscircuits.viewer.constants.Constants
import org.citruscircuits.viewer.getTeamObjectByKey
import java.util.regex.Pattern

// Custom list adapter class with aq object handling to display the custom cell for the match schedule.
class RankingListAdapter(
    private val activity: Activity,
    private val listContents: List<String>
) : BaseAdapter() {

    private val inflater = LayoutInflater.from(activity)

    // Return the size of the match schedule.
    override fun getCount(): Int {
        return listContents.size
    }

    // Return the Match object given the match number.
    override fun getItem(position: Int): Any {
        return listContents[position]
    }

    // Return the position of the cell.
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    // Populate the elements of the custom cell.
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val viewHolder: ViewHolder
        val rowView: View?

        if (convertView == null) {
            rowView = inflater.inflate(R.layout.seeding_cell, parent, false)
            viewHolder =
                ViewHolder(
                    rowView
                )
            rowView.tag = viewHolder
        } else {
            rowView = convertView
            viewHolder = rowView.tag as ViewHolder
        }

        viewHolder.tvTeamNumber.text = listContents[position]

        val regex: Pattern = Pattern.compile("[0-9]+" + Regex.escape(".") + "[0-9]+")

        viewHolder.tvDatapointOne.text = getTeamObject(
            "current_rank",
            position
        )
        viewHolder.tvDatapointTwo.text =
            if (getTeamObject(
                    "current_avg_rps",
                    position
                )?.let {
                    regex.matcher(
                        it
                    ).matches()
                }
                == true
            ) {
                (("%.2f").format(
                    getTeamObject(
                        "current_avg_rps",
                        position
                    )?.toFloat() ?: Constants.NULL_CHARACTER
                )
                        )
            } else {
                getTeamObject(
                    "current_avg_rps",
                    position
                )
            }
        viewHolder.tvDatapointThree.text = getTeamObject(
            "current_rps",
            position
        )
        viewHolder.tvDatapointFour.text = if (getTeamObject(
                "predicted_rps",
                position
            )?.let {
                regex.matcher(
                    it
                ).matches()
            }
            == true
        ) {
            (("%.1f").format(
                getTeamObject(
                    "predicted_rps",
                    position
                )?.toFloat() ?: Constants.NULL_CHARACTER
            )
                    )
        } else {
            getTeamObject(
                "predicted_rps",
                position
            )
        }
        viewHolder.tvDatapointFive.text = getTeamObject(
            "predicted_rank",
            position
        )

        return rowView!!
    }

    private fun getTeamObject(field: String, position: Int): String? {
        return getTeamObjectByKey(
            listContents[position],
            field
        )
    }
}

// View holder class to handle the elements used in the custom cells.
private class ViewHolder(view: View?) {
    val tvTeamNumber = view?.findViewById(R.id.tv_team_number) as TextView
    val tvDatapointOne = view?.findViewById(R.id.tv_datapoint_one) as TextView
    val tvDatapointTwo = view?.findViewById(R.id.tv_datapoint_two) as TextView
    val tvDatapointThree = view?.findViewById(R.id.tv_datapoint_three) as TextView
    val tvDatapointFour = view?.findViewById(R.id.tv_datapoint_four) as TextView
    val tvDatapointFive = view?.findViewById(R.id.tv_datapoint_five) as TextView
}