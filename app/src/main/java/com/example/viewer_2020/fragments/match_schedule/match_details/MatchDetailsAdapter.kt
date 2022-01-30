package com.example.viewer_2020.fragments.match_schedule.match_details

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.example.viewer_2020.R
import com.example.viewer_2020.constants.Constants
import com.example.viewer_2020.constants.Translations
import com.example.viewer_2020.getTeamDataValue
import kotlinx.android.synthetic.main.match_details_cell.view.*
import kotlinx.android.synthetic.main.match_details_cell.view.tv_datapoint_name
import java.lang.Float
import java.util.regex.Pattern

class MatchDetailsAdapter (
    private val context: FragmentActivity,
    private val datapointsDisplay: List<String>,
    private val teamNumber: List<String>
) : BaseAdapter() {

    private val inflater: LayoutInflater
            = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    // Return the size of the list of the data points to be displayed.
    override fun getCount(): Int {
        return datapointsDisplay.size
    }

    // Returns the specific data point given the position of the data point.
    override fun getItem(position: Int): Any {
        return datapointsDisplay[position] as Any
    }

    // Returns the position of the cell.
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    // Populate the elements of the custom cell.
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val rowView = inflater.inflate(R.layout.match_details_cell, parent, false)
            rowView.tv_datapoint_name.text =
                    Translations.ACTUAL_TO_HUMAN_READABLE[datapointsDisplay[position]]
                            ?: datapointsDisplay[position]

<<<<<<< HEAD
            if ((datapointsDisplay[position] == "Auto") or (datapointsDisplay[position] == "Tele") or (datapointsDisplay[position] == "Endgame") or (datapointsDisplay[position] == "Pit Data")) {
=======
            if (datapointsDisplay[position] in Constants.CATEGORY_NAMES) {
>>>>>>> 3e14a203e8397b7345d40b51cf52d7589afc689e
                val noWidth = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 0f)
                val allWidth = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1f)
                rowView.tv_datapoint_name.gravity = Gravity.CENTER_HORIZONTAL
                rowView.tv_datapoint_name.setTextColor(
                        ContextCompat.getColor(
                                context,
                                R.color.Black
                        ))
                rowView.tv_datapoint_name.setBackgroundColor(ContextCompat.getColor(
                        context,
                        R.color.LightGray
                ))
                rowView.tv_datapoint_name.layoutParams = allWidth
                rowView.tv_team_one_md.layoutParams = noWidth
                rowView.tv_team_two_md.layoutParams = noWidth
                rowView.tv_team_three_md.layoutParams = noWidth
                rowView.tv_team_four_md.layoutParams = noWidth
                rowView.tv_team_five_md.layoutParams = noWidth
                rowView.tv_team_six_md.layoutParams = noWidth
                rowView.tv_team_one_md.text = ""
                rowView.tv_team_two_md.text = ""
                rowView.tv_team_three_md.text = ""
                rowView.tv_team_four_md.text = ""
                rowView.tv_team_five_md.text = ""
                rowView.tv_team_six_md.text = ""
            } else {
                val values = listOf<TextView>(
                        rowView.tv_team_one_md, rowView.tv_team_two_md, rowView.tv_team_three_md,
                        rowView.tv_team_four_md, rowView.tv_team_five_md, rowView.tv_team_six_md
                )

                for (x in 0..5) {
                    getTeamValue(values[x], datapointsDisplay[position], teamNumber[x])
                }
            }
            return rowView
        }
    }

    fun getTeamValue(textView: TextView, field: String, teamNumber: String) {
        val regex: Pattern = Pattern.compile("-?" +"[0-9]+" + Regex.escape(".") + "[0-9]+")
                //if datafield is a float, round datapoint. Otherwise, display returned string from getTeamDataValue
        if (regex.matcher(getTeamDataValue(teamNumber, field)).matches()) {
            if (field in Constants.DRIVER_DATA) {
                textView.text  = ("%.2f").format(Float.parseFloat(getTeamDataValue(teamNumber, field)))
            } else {
                textView.text  = ("%.1f").format(Float.parseFloat(getTeamDataValue(teamNumber, field)))
            }
        } else {
        textView.text = getTeamDataValue(teamNumber, field)
        }
    }