package com.example.viewer_2020.fragments.match_schedule.match_details

import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.example.viewer_2020.R
import com.example.viewer_2020.constants.Translations
import com.example.viewer_2020.getTeamDataValue
import kotlinx.android.synthetic.main.match_details_cell.view.*
import kotlinx.android.synthetic.main.match_details_cell.view.tv_datapoint_name
import kotlinx.android.synthetic.main.team_details_cell.view.*
import java.lang.Float
import java.util.regex.Pattern

class MatchDetailsAdapter (
    private val context: FragmentActivity,
    private val datapointsDisplayed: List<String>,
    private val teamNumber: List<String>
) : BaseAdapter() {

    private val inflater: LayoutInflater
            = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    // Return the size of the list of the data points to be displayed.
    override fun getCount(): Int {
        return datapointsDisplayed.size
    }

    // Returns the specific data point given the position of the data point.
    override fun getItem(position: Int): Any {
        return datapointsDisplayed[position] as Any
    }

    // Returns the position of the cell.
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    // Populate the elements of the custom cell.
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val rowView = inflater.inflate(R.layout.match_details_cell, parent, false)
        rowView.tv_datapoint_name.text =
            Translations.ACTUAL_TO_HUMAN_READABLE[datapointsDisplayed[position]] ?: datapointsDisplayed[position]
        if((datapointsDisplayed[position] == "Auto") or (datapointsDisplayed[position] == "Tele") or (datapointsDisplayed[position] == "Endgame") or (datapointsDisplayed[position] == "Other")){

            rowView.tv_datapoint_name.setTextColor(
                ContextCompat.getColor(
                context,
                R.color.Black
            ))
            rowView.tv_datapoint_name.setBackgroundColor(ContextCompat.getColor(
                context,
                R.color.LightGray
            ))
            rowView.tv_team_one_md.setBackgroundColor(ContextCompat.getColor(
                context,
                R.color.LightGray
            ))
            rowView.tv_team_two_md.setBackgroundColor(ContextCompat.getColor(
                context,
                R.color.LightGray
            ))
            rowView.tv_team_three_md.setBackgroundColor(ContextCompat.getColor(
                context,
                R.color.LightGray
            ))
            rowView.tv_team_four_md.setBackgroundColor(ContextCompat.getColor(
                context,
                R.color.LightGray
            ))
            rowView.tv_team_five_md.setBackgroundColor(ContextCompat.getColor(
                context,
                R.color.LightGray
            ))
            rowView.tv_team_six_md.setBackgroundColor(ContextCompat.getColor(
                context,
                R.color.LightGray
            ))
            rowView.tv_team_one_md.text = ""
            rowView.tv_team_two_md.text = ""
            rowView.tv_team_three_md.text = ""
            rowView.tv_team_four_md.text = ""
            rowView.tv_team_five_md.text = ""
            rowView.tv_team_six_md.text = ""
        }
        else {
            var values = listOf<TextView>(
                rowView.tv_team_one_md, rowView.tv_team_two_md, rowView.tv_team_three_md,
                rowView.tv_team_four_md, rowView.tv_team_five_md, rowView.tv_team_six_md
            )

            for (x in 0..5) {
                getTeamValue(values[x], datapointsDisplayed[position], teamNumber[x])
            }
        }
        return rowView
    }
    fun getTeamValue(textView: TextView, field: String, teamNumber: String) {
        val regex: Pattern = Pattern.compile("[0-9]+" + Regex.escape(".") + "[0-9]+")
        textView.text =
                //if datafield is a float, round datapoint. Otherwise, display returned string from getTeamDataValue
            if (regex.matcher(getTeamDataValue(teamNumber, field)).matches()) {
                ("%.1f").format(Float.parseFloat(getTeamDataValue(teamNumber, field)))
            } else {
                getTeamDataValue(teamNumber, field)
            }
    }
}