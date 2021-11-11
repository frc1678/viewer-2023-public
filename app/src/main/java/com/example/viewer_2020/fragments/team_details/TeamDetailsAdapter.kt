package com.example.viewer_2020.fragments.team_details

import android.content.Context
import android.graphics.Color
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.example.viewer_2020.R
import com.example.viewer_2020.constants.Constants
import com.example.viewer_2020.constants.Translations
import com.example.viewer_2020.getTeamDataValue
import kotlinx.android.synthetic.main.team_details_cell.view.*
import java.lang.Float.parseFloat
import java.util.regex.Pattern

// Custom list adapter class for each list view of the six teams featured in every MatchDetails display.
// TODO implement a type 'Team' object parameter to access the team data for the team number.
class TeamDetailsAdapter(
    private val context: FragmentActivity,
    private val datapointsDisplayed: List<String>,
    private val teamNumber: String
) : BaseAdapter() {

    private val inflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    // Return the size of the list of the data points to be displayed.
    override fun getCount(): Int {
        return datapointsDisplayed.size
    }

    // Returns the specific data point given the position of the data point.
    override fun getItem(position: Int): Any {
        return datapointsDisplayed[position]
    }

    // Returns the position of the cell.
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    // Populate the elements of the custom cell.
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val regex: Pattern = Pattern.compile("-?" +"[0-9]+" + Regex.escape(".") + "[0-9]+")
        val rowView = inflater.inflate(R.layout.team_details_cell, parent, false)
        rowView.tv_datapoint_name.text =
            Translations.ACTUAL_TO_HUMAN_READABLE[datapointsDisplayed[position]]
                ?: datapointsDisplayed[position]
        if ((datapointsDisplayed[position] == "Auto") or(datapointsDisplayed[position] == "Tele") or (datapointsDisplayed[position] == "Endgame") or (datapointsDisplayed[position] == "Pit Data")){
            rowView.tv_datapoint_name.setTextSize(TypedValue.COMPLEX_UNIT_SP,28F)
            rowView.tv_ranking.setBackgroundColor(ContextCompat.getColor(
                context,
                R.color.LightGray
            ))
            rowView.tv_datapoint_name.setBackgroundColor(ContextCompat.getColor(
                context,
                R.color.LightGray
            ))
            rowView.tv_datapoint_value.setBackgroundColor(ContextCompat.getColor(
                context,
                R.color.LightGray
            ))
            rowView.tv_datapoint_name.setTextColor(
                ContextCompat.getColor(
                context,
                R.color.Black
            ))
            rowView.tv_datapoint_value.text = ""
        }

        else {
                if(regex.matcher(getTeamDataValue(
                        teamNumber,
                        datapointsDisplayed[position])).matches()) {
                    if(datapointsDisplayed[position] in Constants.DRIVER_DATA){
                        rowView.tv_datapoint_value.text = ("%.2f").format(parseFloat(getTeamDataValue(teamNumber, datapointsDisplayed[position])))
                    }else{
                        rowView.tv_datapoint_value.text = ("%.1f").format(parseFloat(getTeamDataValue(teamNumber, datapointsDisplayed[position])))
                    }
                } else {
                    rowView.tv_datapoint_value.text = getTeamDataValue(
                        teamNumber,
                        datapointsDisplayed[position]
                    )
                }
        }
        return rowView
    }
}