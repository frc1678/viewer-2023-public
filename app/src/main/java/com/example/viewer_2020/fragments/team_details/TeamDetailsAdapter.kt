package com.example.viewer_2020.fragments.team_details

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.Environment
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
import com.example.viewer_2020.R
import com.example.viewer_2020.constants.Constants
import com.example.viewer_2020.constants.Translations
import com.example.viewer_2020.getTeamDataValue
import kotlinx.android.synthetic.main.team_details_cell.view.*
import java.lang.Float.parseFloat
import java.util.regex.Pattern
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.viewer_2020.fragments.team_ranking.TeamRankingFragment
import com.example.viewer_2020.getRankingTeam

// Custom list adapter class for each list view of the six teams featured in every MatchDetails display.
// TODO implement a type 'Team' object parameter to access the team data for the team number.
class TeamDetailsAdapter(
    private val context: FragmentActivity,
    private val datapointsDisplayed: List<String>,
    private val teamNumber: String
) : RecyclerView.Adapter<TeamDetailsAdapter.ViewHolder>() {

    init {
        super.setHasStableIds(true)
        Log.d("match-details", datapointsDisplayed.toString())
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ranking: TextView = view.findViewById(R.id.tv_ranking)
        val datapointName: TextView = view.findViewById(R.id.tv_datapoint_name)
        val datapointValue: TextView = view.findViewById(R.id.tv_datapoint_value)
        val root = view

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        val rowView = layoutInflater
            .inflate(R.layout.team_details_cell, viewGroup, false)



        return ViewHolder(rowView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val graphsFragment = GraphsFragment()
        val graphsFragmentArguments = Bundle()
        val e = datapointsDisplayed[position]
        val regex: Pattern = Pattern.compile("-?" + "[0-9]+" + Regex.escape(".") + "[0-9]+")
        var isHeader = false
        holder.datapointName.text =
            Translations.ACTUAL_TO_HUMAN_READABLE[e]
                ?: e
        if (e in Constants.CATEGORY_NAMES) {
            holder.setIsRecyclable(false)
            isHeader = true
            holder.root.layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT
            holder.datapointName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 28F)
            holder.datapointName.gravity = Gravity.CENTER_HORIZONTAL
            val noWidth = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 0f)
            val allWidth = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1f)
            holder.ranking.layoutParams = noWidth
            holder.datapointName.layoutParams = allWidth
            holder.datapointValue.layoutParams = noWidth
            holder.datapointName.setBackgroundColor(
                ContextCompat.getColor(
                    context,
                    R.color.LightGray
                )
            )
            holder.datapointName.setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.Black
                )
            )
            holder.datapointValue.text = ""
        } else {
            if (regex.matcher(
                    getTeamDataValue(
                        teamNumber,
                        e
                    )
                ).matches()
            ) {
                if (e in Constants.DRIVER_DATA) {
                    holder.datapointValue.text = ("%.2f").format(
                        parseFloat(
                            getTeamDataValue(
                                teamNumber,
                                e
                            )
                        )
                    )
                } else {
                    holder.datapointValue.text = ("%.1f").format(
                        parseFloat(
                            getTeamDataValue(
                                teamNumber,
                                e
                            )
                        )
                    )
                }
            } else {
                holder.datapointValue.text = getTeamDataValue(
                    teamNumber,
                    e
                )
            }
            if (e in Constants.RANKABLE_FIELDS) {
                holder.ranking.text =
                    getRankingTeam(teamNumber, e, Constants.RANKABLE_FIELDS[e]!!)
            }
        }



        //Headers don't need on click handlers
        if (!isHeader) {
            //Some fields (eg drivetrain_motor_type) don't need to be rankable
            if (e in Constants.RANKABLE_FIELDS.keys) {
                holder.root.setOnLongClickListener {
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

//                println((it.rootView.findViewById(R.id.nav_host_fragment) as ViewGroup))

                    //the reason i have to do so many .parent calls is because this cell is so far back the the stack
                    //normally i would have done it from the fragment but i forgot about that
                    //this could also be fixed with some other way to get the id
                    //if something breaks from someone just changing xml. this is why
                    teamRankingFragmentTransaction.addToBackStack(null).replace(
                        (it.rootView.findViewById(R.id.nav_host_fragment) as ViewGroup).id,
                        teamRankingFragment
                    ).commit()

                    println(e)
                    //return true says that the onLongClick was handled successfully so haptic feedback can happen correctly
                    return@setOnLongClickListener true
                }
            }
            holder.root.setOnClickListener() {
                if (Constants.GRAPHABLE.contains(datapointsDisplayed[position]) or
                    Constants.GRAPHABLE_BOOL.contains(datapointsDisplayed[position]) or
                    Constants.GRAPHABLE_CLIMB_TIMES.contains(datapointsDisplayed[position])
                ) {
                    graphsFragmentArguments.putString(Constants.TEAM_NUMBER, teamNumber)
                    graphsFragmentArguments.putString("datapoint", datapointsDisplayed[position])
                    graphsFragment.arguments = graphsFragmentArguments
                    context.supportFragmentManager.beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.nav_host_fragment, graphsFragment, "graphs")
                        .commit()
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return datapointsDisplayed.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

}