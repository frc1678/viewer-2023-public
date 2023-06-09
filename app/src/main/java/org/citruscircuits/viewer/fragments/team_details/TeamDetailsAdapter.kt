package org.citruscircuits.viewer.fragments.team_details

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import kotlinx.android.synthetic.main.team_details_cell.view.*
import org.citruscircuits.viewer.*
import org.citruscircuits.viewer.constants.Constants
import org.citruscircuits.viewer.constants.Translations
import org.citruscircuits.viewer.fragments.match_schedule.MatchScheduleFragment
import org.citruscircuits.viewer.fragments.notes.NotesFragment
import org.citruscircuits.viewer.fragments.team_ranking.TeamRankingFragment
import org.citruscircuits.viewer.fragments.team_ranking.TeamRankingListAdapter
import java.lang.Float.parseFloat
import java.util.regex.Pattern

// Custom list adapter class for each list view of the six teams featured in every MatchDetails display.
// TODO implement a type 'Team' object parameter to access the team data for the team number.
class TeamDetailsAdapter(
    private val context: FragmentActivity,
    private val datapointsDisplayed: List<String>,
    private val teamNumber: String,
    private val visualDataBar: Boolean
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
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        val graphsFragment = GraphsFragment()
        val modeStartPositionFragment = ModeStartPositionFragment()
        val graphsFragmentArguments = Bundle()
        val modeStartPositionFragmentArguments = Bundle()
        var e = getItem(position)
        val regex: Pattern = Pattern.compile("-?" + "[0-9]+" + Regex.escape(".") + "[0-9]+")
        if (e == "Visual Data Bars") {
            return View(context)
        }
        val rowView = inflater.inflate(R.layout.team_details_cell, parent, false)
        var isHeader = false
        rowView.tv_datapoint_name.text =
            Translations.ACTUAL_TO_HUMAN_READABLE[e]
                ?: e
        if (e in listOf(
                "Auto",
                "L4M Auto",
                "Tele",
                "L4M Tele",
                "Endgame",
                "L4M Endgame",
                "Pit Data",
                "See Matches",
                "Notes",
                "Notes Label"
            )
        ) {
            isHeader = true
            rowView.tv_datapoint_name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 28F)
            rowView.tv_datapoint_name.gravity = Gravity.CENTER_HORIZONTAL
            val noWidth = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 0f)
            val allWidth = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1f)
            rowView.tv_ranking.layoutParams = noWidth
            rowView.tv_datapoint_name.layoutParams = allWidth
            rowView.tv_datapoint_value.layoutParams = noWidth
            if (e == "See Matches") {
                rowView.tv_datapoint_name.setBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.MediumGray
                    )
                )
            } else if (e == "Notes Label") {
                rowView.tv_datapoint_name.setBackgroundColor(context.resources.getColor(R.color.Highlighter))
                rowView.tv_datapoint_name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18F)
                rowView.tv_datapoint_name.text = "Notes (click below to edit)"
            } else {
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
            }
            rowView.tv_datapoint_value.text = ""

            if (e == "Notes") {
                Log.d("notes", "SETTING UP NOTES CELL IN TEAM DETAILS")
                rowView.tv_datapoint_name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14F)
                rowView.tv_datapoint_name.setBackgroundColor(context.resources.getColor(R.color.Highlighter))
                rowView.setOnClickListener {
                    Log.d("notes", "notes button clicked in team details")
                    val notesFragment = NotesFragment()
                    val notesFragmentArgs = Bundle()
                    notesFragmentArgs.putString(Constants.TEAM_NUMBER, teamNumber)
                    notesFragment.arguments = notesFragmentArgs
                    val notesFragmentTransaction =
                        context.supportFragmentManager.beginTransaction()
                    notesFragmentTransaction.addToBackStack(null).replace(
                        (it.rootView.findViewById(R.id.nav_host_fragment) as ViewGroup).id,
                        notesFragment
                    ).commit()
                }
                if (MainViewerActivity.notesCache.containsKey(teamNumber)) {
                    rowView.tv_datapoint_name.text = MainViewerActivity.notesCache[teamNumber]
                } else {
                    Log.d("notes", "notesCache does not contain team $teamNumber")
                    rowView.tv_datapoint_name.text = ""
                }
            }
        } else {
            if (regex.matcher(
                    getTeamDataValue(
                        teamNumber,
                        e
                    )
                ).matches()
            ) {
                if (getTeamDataValue(teamNumber, e) != null) {
                    rowView.tv_datapoint_value.text = ("%.1f").format(
                        getTeamDataValue(
                            teamNumber,
                            e
                        )?.let {
                            parseFloat(
                                it
                            )
                        }
                    )
                } else {
                    rowView.tv_datapoint_value.text = ("%.1f").format(Constants.NULL_CHARACTER)
                }
            } else {
                rowView.tv_datapoint_value.text = getTeamDataValue(
                    teamNumber,
                    e
                )
            }

            if (Constants.PERCENT_DATA.contains(e)) {
                rowView.tv_datapoint_value.text = "${rowView.tv_datapoint_value.text}%"
                if (visualDataBar) {
                    (rowView.data_bar.layoutParams as LinearLayout.LayoutParams).weight =
                        (getTeamDataValue(teamNumber, e)!!.toFloat()) / 100
                }
            }
            if (visualDataBar && (getTeamDataValue(teamNumber, e)!! != Constants.NULL_CHARACTER) && ((getRankingList(e).last().value)!! != Constants.NULL_CHARACTER)) {
                if ((Constants.PIT_DATA.contains(e)) || (Constants.FIELDS_TO_BE_DISPLAYED_RANKING.contains(e))
                    || ("pickability" in e) || ("start" in e) || ("matches_played" == e)
                ) {
                    (rowView.data_bar.layoutParams as LinearLayout.LayoutParams).weight = 0F
                    (rowView.data_bar_reverse.layoutParams as LinearLayout.LayoutParams).weight = 1F
                } else if (("incap" in e) || ("foul" in e) || ("tippy" in e)) { //incap, foul, and tippiness rankings are reversed, so divides by last value in rankings
                    (rowView.data_bar_reverse.layoutParams as LinearLayout.LayoutParams).weight =
                        ((getTeamDataValue(teamNumber, e)!!.toFloat()) /
                                (getRankingList(e).last().value)!!.toFloat())
                    (rowView.data_bar.layoutParams as LinearLayout.LayoutParams).weight =
                        1 - (rowView.data_bar_reverse.layoutParams as LinearLayout.LayoutParams).weight
                    rowView.data_bar.setBackgroundColor(
                        context.resources.getColor(R.color.White)
                    )
                    rowView.data_bar_reverse.setBackgroundColor(
                        context.resources.getColor(R.color.Pink)
                    )
                } else if (("max" in e) || ("avg" in e) || (Constants.DRIVER_DATA.contains(e)) ||
                    ("defense" in e) || ("success" in e) || ("attempt" in e)
                ) {
                    //changes weight based on how datapoint compares to highest rank of that datapoint
                    (rowView.data_bar.layoutParams as LinearLayout.LayoutParams).weight =
                        ((getTeamDataValue(teamNumber, e)!!.toFloat()) /
                                (getRankingList(e).first().value)!!.toFloat())
                    (rowView.data_bar_reverse.layoutParams as LinearLayout.LayoutParams).weight = 1 - (rowView.data_bar.layoutParams as LinearLayout.LayoutParams).weight
                }

                if ("cube" in e) {
                    rowView.data_bar.setBackgroundColor(
                        context.resources.getColor(R.color.Cube)
                    )
                } else if ("cone" in e) {
                    rowView.data_bar.setBackgroundColor(
                        context.resources.getColor(R.color.Cone)
                    )
                }
            } else {
                (rowView.data_bar.layoutParams as LinearLayout.LayoutParams).weight = 0F
                (rowView.data_bar_reverse.layoutParams as LinearLayout.LayoutParams).weight = 0F
                if ("cube" in e) {
                    rowView.setBackgroundColor(
                        context.resources.getColor(R.color.Cube)
                    )
                } else if ("cone" in e) {
                    rowView.setBackgroundColor(
                        context.resources.getColor(R.color.Cone)
                    )
                }
            }
        }
        if (e in Constants.RANKABLE_FIELDS) {
            rowView.tv_ranking.text = if (e in Constants.PIT_DATA) "" else getRankingTeam(
                teamNumber,
                e
            )?.placement?.toString() ?: Constants.NULL_CHARACTER
        }
        //Only add graphable onclick listener if Constants contains the datapoint
        if (Constants.GRAPHABLE.contains(datapointsDisplayed[position])) {
            rowView.setOnClickListener {
                if (datapointsDisplayed[position] == "mode_start_position") {
                    modeStartPositionFragmentArguments.putString(Constants.TEAM_NUMBER, teamNumber)
                    modeStartPositionFragmentArguments.putString(
                        "datapoint",
                        Constants.GRAPHABLE[datapointsDisplayed[position]]
                    )
                    modeStartPositionFragment.arguments = modeStartPositionFragmentArguments
                    context.supportFragmentManager.beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.nav_host_fragment, modeStartPositionFragment, "mode_start_position")
                        .commit()
                }
                else {
                    graphsFragmentArguments.putString(Constants.TEAM_NUMBER, teamNumber)
                    //Get the tim datapoint from the team datapoint and add as an argument
                    graphsFragmentArguments.putString(
                        "datapoint",
                        Constants.GRAPHABLE[datapointsDisplayed[position]]
                    )
                    graphsFragment.arguments = graphsFragmentArguments
                    context.supportFragmentManager.beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.nav_host_fragment, graphsFragment, "graphs")
                        .commit()
                }
            }
        }

        if (e == "See Matches") {
            rowView.setOnClickListener {
                val matchScheduleFragment = MatchScheduleFragment()
                val matchScheduleFragmentArguments = Bundle()
                val matchScheduleFragmentTransaction =
                    context.supportFragmentManager.beginTransaction()
                matchScheduleFragmentArguments.putString(Constants.TEAM_NUMBER, teamNumber)
                matchScheduleFragment.arguments = matchScheduleFragmentArguments
                matchScheduleFragmentTransaction.addToBackStack(null).replace(
                    (it.rootView.findViewById(R.id.nav_host_fragment) as ViewGroup).id,
                    matchScheduleFragment
                ).commit()
            }
        }

        //Headers don't need on click handlers
        if (!isHeader) {
            //Some fields (eg drivetrain_motor_type) don't need to be rankable
            if (e in Constants.RANKABLE_FIELDS.keys) {
                rowView.setOnLongClickListener {
                    val teamRankingFragment = TeamRankingFragment()
                    val teamRankingFragmentArguments = Bundle()
                    val teamRankingFragmentTransaction =
                        context.supportFragmentManager.beginTransaction()

                    //add the data point key to the bundle
                    teamRankingFragmentArguments.putString(TeamRankingFragment.DATA_POINT, e)
                    //add the team number to the bundle
                    teamRankingFragmentArguments.putString(
                        TeamRankingFragment.TEAM_NUMBER,
                        teamNumber
                    )

                    //attach the bundle to the fragment
                    teamRankingFragment.arguments = teamRankingFragmentArguments

                    teamRankingFragmentTransaction.addToBackStack(null).replace(
                        (it.rootView.findViewById(R.id.nav_host_fragment) as ViewGroup).id,
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
