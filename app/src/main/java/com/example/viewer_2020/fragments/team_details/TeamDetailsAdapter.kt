package com.example.viewer_2020.fragments.team_details

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.Environment
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.example.viewer_2020.R
import com.example.viewer_2020.constants.Constants
import com.example.viewer_2020.constants.Translations
import com.example.viewer_2020.getTeamDataValue
import kotlinx.android.synthetic.main.team_details_cell.view.*
import java.lang.Float.parseFloat
import java.util.regex.Pattern
import android.widget.FrameLayout
import com.example.viewer_2020.fragments.team_ranking.TeamRankingFragment

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
    override fun getItem(position: Int): String {
        return datapointsDisplayed[position]
    }

    // Returns the position of the cell.
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    // Populate the elements of the custom cell.
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val e = getItem(position)
        val regex: Pattern = Pattern.compile("-?" + "[0-9]+" + Regex.escape(".") + "[0-9]+")
        val rowView = inflater.inflate(R.layout.team_details_cell, parent, false)
        var isHeader = false
        rowView.tv_datapoint_name.text =
            Translations.ACTUAL_TO_HUMAN_READABLE[e]
                ?: e
        if ((e == "Auto") or (e == "Tele") or (e == "Endgame") or (e == "Pit Data")) {
            isHeader = true
            rowView.tv_datapoint_name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 28F)
            rowView.tv_datapoint_name.gravity = Gravity.CENTER_HORIZONTAL
            val noWidth = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 0f)
            val allWidth = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1f)
            rowView.tv_ranking.layoutParams = noWidth
            rowView.tv_datapoint_name.layoutParams = allWidth
            rowView.tv_datapoint_value.layoutParams = noWidth
            rowView.tv_datapoint_name.setBackgroundColor(
                ContextCompat.getColor(
                    context,
                    R.color.LightGray
                )
            )
            rowView.tv_datapoint_name.setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.Black
                )
            )
            rowView.tv_datapoint_value.text = ""
        } else {
            if (regex.matcher(
                    getTeamDataValue(
                        teamNumber,
                        e
                    )
                ).matches()
            ) {
                if (e in Constants.DRIVER_DATA) {
                    rowView.tv_datapoint_value.text = ("%.2f").format(
                        parseFloat(
                            getTeamDataValue(
                                teamNumber,
                                e
                            )
                        )
                    )
                } else {
                    rowView.tv_datapoint_value.text = ("%.1f").format(
                        parseFloat(
                            getTeamDataValue(
                                teamNumber,
                                e
                            )
                        )
                    )
                }
            } else {
                rowView.tv_datapoint_value.text = getTeamDataValue(
                    teamNumber,
                    e
                )
            }
        }
        //Headers don't need on click handlers
        if (!isHeader) {
            //Some fields (eg drivetrain_motor_type) don't need to be rankable
            if(e in Constants.RANKABLE_FIELDS.keys){
                rowView.setOnLongClickListener {
                    val teamRankingFragment = TeamRankingFragment()
                    val teamRankingFragmentArguments = Bundle()
                    val teamRankingFragmentTransaction = context.supportFragmentManager.beginTransaction()

                    //add the data point key to the bundle
                    teamRankingFragmentArguments.putString(TeamRankingFragment.DATA_POINT, e)
                    //add the team number to the bundle
                    teamRankingFragmentArguments.putString(TeamRankingFragment.TEAM_NUMBER, teamNumber)

                    //attach the bundle to the fragment
                    teamRankingFragment.arguments = teamRankingFragmentArguments

//                println((it.parent.parent.parent.parent as ViewGroup))

                    //the reason i have to do so many .parent calls is because this cell is so far back the the stack
                    //normally i would have done it from the fragment but i forgot about that
                    //this could also be fixed with some other way to get the id
                    //if something breaks from someone just changing xml. this is why
                    teamRankingFragmentTransaction.addToBackStack(null).replace(
                        (it.parent.parent.parent.parent as ViewGroup).id,
                        teamRankingFragment
                    ).commit()

                    println(e)
                    //return true says that the onLongClick was handled successfully so haptic feedback can happen correctly
                    return@setOnLongClickListener true
                }
            }

        }

        return rowView
    }
}