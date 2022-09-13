package com.example.viewer_2022.fragments.team_list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageButton
import com.example.viewer_2022.MainViewerActivity.StarredTeams
import com.example.viewer_2022.R
import com.example.viewer_2022.getTeamDataValue
import kotlinx.android.synthetic.main.pickability_cell.view.tv_team_number
import kotlinx.android.synthetic.main.team_list_cell.view.*

/**
 * Adapter for the team list fragment.
 */
class TeamListAdapter(private val context: Context, private val items: List<String>) :
    BaseAdapter() {
    private val inflater = LayoutInflater.from(context)
    override fun getCount(): Int {
        return items.size;
    }

    override fun getItem(i: Int): String {
        return items.elementAt(i)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    // populates the team list cells with the correct team number and name
    override fun getView(i: Int, view: View?, parent: ViewGroup?): View {
        val teamNumber = getItem(i)
        val rowView = inflater.inflate(R.layout.team_list_cell, parent, false)
        rowView.tv_team_number.text = teamNumber

        // Set star icon if the team is starred
        if (StarredTeams.contains(teamNumber)) {
            (rowView.btn_star as ImageButton).setImageResource(R.drawable.ic_baseline_star_36)
        } else {
            (rowView.btn_star as ImageButton).setImageResource(R.drawable.dot)
        }
        // Toggle star if the icon is clicked
        rowView.btn_star.setOnClickListener {
            if (StarredTeams.contains(teamNumber)) {
                StarredTeams.remove(teamNumber)
                (it as ImageButton).setImageResource(R.drawable.dot)
            } else {
                StarredTeams.add(teamNumber)
                (it as ImageButton).setImageResource(R.drawable.ic_baseline_star_36)
            }
        }

        rowView.tv_team_name.text = getTeamDataValue(teamNumber, "team_name")
        return rowView
    }

}
