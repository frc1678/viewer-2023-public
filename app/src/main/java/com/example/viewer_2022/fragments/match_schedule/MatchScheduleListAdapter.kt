/*
* MatchScheduleListAdapter.kt
* viewer
*
* Created on 1/26/2020
* Copyright 2020 Citrus Circuits. All rights reserved.
*/

package com.example.viewer_2022.fragments.match_schedule

import android.content.Context
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.Typeface.DEFAULT
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.example.viewer_2022.MainViewerActivity
import com.example.viewer_2022.MainViewerActivity.StarredMatches
import com.example.viewer_2022.R
import com.example.viewer_2022.constants.Constants
import com.example.viewer_2022.data.Match
import com.example.viewer_2022.fragments.match_details.MatchDetailsFragment
import com.example.viewer_2022.getAllianceInMatchObjectByKey

/**
 * Custom list adapter class with Match object handling to display the custom cell for the match schedule.
 */
class MatchScheduleListAdapter(
    private val context: FragmentActivity,
    private var matchContents: Map<String, Match>,
    private var scheduleType: Constants.ScheduleType,
    private var listView: ListView
) : BaseAdapter() {

    private val inflater = LayoutInflater.from(context)

    /**
     * @return the size of the match schedule.
     */
    override fun getCount() = matchContents.size

    /**
     * @return the Match object given the match number.
     */
    override fun getItem(position: Int): Match? = when (scheduleType) {
        Constants.ScheduleType.OUR_MATCHES, Constants.ScheduleType.STARRED_MATCHES -> {
            matchContents[matchContents.keys.toList()[position]]
        }

        else -> matchContents[(position + 1).toString()]
    }

    fun updateData(newData: Map<String, Match>, oneTeam: Constants.ScheduleType) {
        matchContents = newData
        scheduleType = oneTeam
        notifyDataSetChanged()
    }

    /**
     * @return the position of the cell.
     */
    override fun getItemId(position: Int) = position.toLong()

    /**
     * Populate the elements of the custom cell.
     */
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val viewHolder: ViewHolder
        val rowView: View?
        val matchNumber: String = when (scheduleType) {
            Constants.ScheduleType.OUR_MATCHES, Constants.ScheduleType.STARRED_MATCHES -> {
                matchContents.keys.toList()[position]
            }

            else -> (position + 1).toString()
        }

        // Recycle previously inflated view if available
        if (convertView == null) {
            rowView = inflater.inflate(R.layout.match_schedule_cell, parent, false)
            viewHolder = ViewHolder(rowView)
            rowView.tag = viewHolder
        } else {
            rowView = convertView
            viewHolder = rowView.tag as ViewHolder
        }

        /**
         * Whether actual data exists for this match. Requires both red and blue to have actual data.
         */
        val hasActualData = getAllianceInMatchObjectByKey(
            Constants.PROCESSED_OBJECT.CALCULATED_PREDICTED_ALLIANCE_IN_MATCH.value,
            Constants.RED,
            matchNumber,
            "has_actual_data"
        ).toBoolean() && getAllianceInMatchObjectByKey(
            Constants.PROCESSED_OBJECT.CALCULATED_PREDICTED_ALLIANCE_IN_MATCH.value,
            Constants.BLUE,
            matchNumber,
            "has_actual_data"
        ).toBoolean()

        // Set the team numbers and default styles
        for (tv in viewHolder.redTeams) {
            tv.paintFlags = 0
            tv.typeface = DEFAULT
            tv.text = matchContents[matchNumber]!!.redTeams[viewHolder.redTeams.indexOf(tv)]
        }
        for (tv in viewHolder.blueTeams) {
            tv.paintFlags = 0
            tv.typeface = DEFAULT
            tv.text = matchContents[matchNumber]!!.blueTeams[viewHolder.blueTeams.indexOf(tv)]
        }

        // Set the match number text
        viewHolder.tvMatchNumber.text = matchNumber

        // Set the styling for the winning alliance
        if (hasActualData) {
            if (getAllianceInMatchObjectByKey(
                    Constants.PROCESSED_OBJECT.CALCULATED_PREDICTED_ALLIANCE_IN_MATCH.value,
                    Constants.RED,
                    matchNumber,
                    "won_match"
                ).toBoolean()
            ) {
                for (tv in viewHolder.redTeams) {
                    tv.paintFlags = Paint.UNDERLINE_TEXT_FLAG
                    tv.typeface = Typeface.DEFAULT_BOLD
                }
            } else {
                for (tv in viewHolder.blueTeams) {
                    tv.paintFlags = Paint.UNDERLINE_TEXT_FLAG
                    tv.typeface = Typeface.DEFAULT_BOLD
                }
            }
        }

        val field = if (hasActualData) "actual_rp" else "predicted_rp"

        // Set the background color based on whether the match has been played
        viewHolder.wholeLine.setBackgroundColor(
            ContextCompat.getColor(context, if (hasActualData) R.color.LightGray else R.color.White)
        )

        // Set the blue predicted score
        if ((!hasActualData) && MainViewerActivity.matchCache[matchNumber]!!.bluePredictedScore != null) {
            // Cache hit
            viewHolder.tvBluePredictedScore.text =
                MainViewerActivity.matchCache[matchNumber]!!.bluePredictedScore.toString()
        } else if (hasActualData && MainViewerActivity.matchCache[matchNumber]!!.blueActualScore != null) {
            // Cache hit
            // noinspection SetTextI18n
            viewHolder.tvBluePredictedScore.text =
                "%.0f".format(MainViewerActivity.matchCache[matchNumber]!!.blueActualScore)
        } else {
            // Cache miss
            val value = getAllianceInMatchObjectByKey(
                Constants.PROCESSED_OBJECT.CALCULATED_PREDICTED_ALLIANCE_IN_MATCH.value,
                Constants.BLUE,
                matchNumber,
                if (hasActualData) "actual_score" else "predicted_score"
            )
            if (value != Constants.NULL_CHARACTER) {
                viewHolder.tvBluePredictedScore.text = (if (hasActualData) "%.0f" else "%.1f").format(value.toFloat())
                if (!hasActualData) {
                    MainViewerActivity.matchCache[matchNumber]!!.bluePredictedScore =
                        "%.1f".format(value.toFloat()).toFloat()
                } else {
                    MainViewerActivity.matchCache[matchNumber]!!.blueActualScore =
                        "%.0f".format(value.toFloat()).toFloat()
                }
            } else {
                viewHolder.tvBluePredictedScore.text = Constants.NULL_PREDICTED_SCORE_CHARACTER
            }
        }

        // Set the red predicted score
        if ((!hasActualData) && MainViewerActivity.matchCache[matchNumber]!!.redPredictedScore != null) {
            // Cache hit
            viewHolder.tvRedPredictedScore.text =
                MainViewerActivity.matchCache[matchNumber]!!.redPredictedScore.toString()
        } else if (hasActualData && MainViewerActivity.matchCache[matchNumber]!!.redActualScore != null) {
            // Cache hit
            // noinspection SetTextI18n
            viewHolder.tvRedPredictedScore.text =
                "%.0f".format(MainViewerActivity.matchCache[matchNumber]!!.redActualScore)
        } else {
            // Cache miss
            val value = if (hasActualData) {
                getAllianceInMatchObjectByKey(
                    Constants.PROCESSED_OBJECT.CALCULATED_PREDICTED_ALLIANCE_IN_MATCH.value,
                    Constants.RED,
                    matchNumber,
                    "actual_score"
                )
            } else {
                getAllianceInMatchObjectByKey(
                    Constants.PROCESSED_OBJECT.CALCULATED_PREDICTED_ALLIANCE_IN_MATCH.value,
                    Constants.RED,
                    matchNumber,
                    "predicted_score"
                )
            }
            if (value != Constants.NULL_CHARACTER) {
                viewHolder.tvRedPredictedScore.text = (if (hasActualData) "%.0f" else "%.1f").format(value.toFloat())
                if (!hasActualData) {
                    MainViewerActivity.matchCache[matchNumber]!!.redPredictedScore =
                        "%.1f".format(value.toFloat()).toFloat()
                } else {
                    MainViewerActivity.matchCache[matchNumber]!!.redActualScore =
                        "%.0f".format(value.toFloat()).toFloat()
                }
            } else {
                viewHolder.tvRedPredictedScore.text = Constants.NULL_PREDICTED_SCORE_CHARACTER
            }
        }

        // Use a different color when there's no predicted score
        for (tv in listOf(viewHolder.tvBluePredictedScore, viewHolder.tvRedPredictedScore)) {
            if (tv.text == Constants.NULL_PREDICTED_SCORE_CHARACTER) {
                tv.setTextColor(ContextCompat.getColor(context, R.color.ElectricGreen))
            } else {
                tv.setTextColor(ContextCompat.getColor(context, R.color.Black))
            }
        }

        // Set the ranking point icons
        red_predicted@ for (tv in listOf(viewHolder.tvRedPredictedRPOne, viewHolder.tvRedPredictedRPTwo)) {
            when (listOf(viewHolder.tvRedPredictedRPOne, viewHolder.tvRedPredictedRPTwo).indexOf(tv)) {
                0 -> {
                    if (hasActualData) {
                        if (MainViewerActivity.matchCache[matchNumber]!!.redActualRPOne != null && MainViewerActivity.matchCache[matchNumber]!!.redActualRPOne!!.toDouble() > Constants.PREDICTED_RANKING_POINT_QUALIFICATION) {
                            tv.setImageResource(R.drawable.cargo_ball)
                            continue@red_predicted
                        }
                    } else if (MainViewerActivity.matchCache[matchNumber]!!.redPredictedRPOne != null && MainViewerActivity.matchCache[matchNumber]!!.redPredictedRPOne!!.toDouble() > Constants.PREDICTED_RANKING_POINT_QUALIFICATION) {
                        tv.setImageResource(R.drawable.cargo_ball)
                        continue@red_predicted
                    }
                }

                1 -> {
                    if (hasActualData) {
                        if (MainViewerActivity.matchCache[matchNumber]!!.redActualRPTwo != null && MainViewerActivity.matchCache[matchNumber]!!.redActualRPTwo!!.toDouble() > Constants.PREDICTED_RANKING_POINT_QUALIFICATION) {
                            tv.setImageResource(R.drawable.pull_up_bars)
                            continue@red_predicted
                        }
                    } else if (MainViewerActivity.matchCache[matchNumber]!!.redPredictedRPTwo != null && MainViewerActivity.matchCache[matchNumber]!!.redPredictedRPTwo!!.toDouble() > Constants.PREDICTED_RANKING_POINT_QUALIFICATION) {
                        tv.setImageResource(R.drawable.pull_up_bars)
                        continue@red_predicted
                    }
                }
            }
            val value = getAllianceInMatchObjectByKey(
                Constants.PROCESSED_OBJECT.CALCULATED_PREDICTED_ALLIANCE_IN_MATCH.value,
                Constants.RED,
                matchNumber,
                field + "${
                    listOf(
                        viewHolder.tvRedPredictedRPOne, viewHolder.tvRedPredictedRPTwo
                    ).indexOf(tv) + 1
                }"
            )
            if (value != Constants.NULL_CHARACTER && value.toDouble() > Constants.PREDICTED_RANKING_POINT_QUALIFICATION) {
                when (listOf(viewHolder.tvRedPredictedRPOne, viewHolder.tvRedPredictedRPTwo).indexOf(tv)) {
                    0 -> {
                        if (hasActualData) {
                            MainViewerActivity.matchCache[matchNumber]!!.redActualRPOne =
                                (("%.0f").format(value.toFloat())).toFloat()
                        } else {
                            MainViewerActivity.matchCache[matchNumber]!!.redPredictedRPOne =
                                (("%.1f").format(value.toFloat())).toFloat()
                        }
                        tv.setImageResource(R.drawable.cargo_ball)
                    }

                    1 -> {
                        if (hasActualData) {
                            MainViewerActivity.matchCache[matchNumber]!!.redActualRPTwo =
                                (("%.0f").format(value.toFloat())).toFloat()
                        } else {
                            MainViewerActivity.matchCache[matchNumber]!!.redPredictedRPTwo =
                                (("%.1f").format(value.toFloat())).toFloat()
                        }
                        tv.setImageResource(R.drawable.pull_up_bars)
                    }
                }
            } else tv.setImageDrawable(null)
        }
        blue_predicted@ for (tv in listOf(viewHolder.tvBluePredictedRPOne, viewHolder.tvBluePredictedRPTwo)) {
            when (listOf(viewHolder.tvBluePredictedRPOne, viewHolder.tvBluePredictedRPTwo).indexOf(tv)) {
                0 -> {
                    if (hasActualData) {
                        if (MainViewerActivity.matchCache[matchNumber]!!.blueActualRPOne != null && MainViewerActivity.matchCache[matchNumber]!!.blueActualRPOne!!.toDouble() > Constants.PREDICTED_RANKING_POINT_QUALIFICATION) {
                            tv.setImageResource(R.drawable.cargo_ball)
                            continue@blue_predicted
                        }
                    } else if (MainViewerActivity.matchCache[matchNumber]!!.bluePredictedRPOne != null && MainViewerActivity.matchCache[matchNumber]!!.bluePredictedRPOne!!.toDouble() > Constants.PREDICTED_RANKING_POINT_QUALIFICATION) {
                        tv.setImageResource(R.drawable.cargo_ball)
                        continue@blue_predicted
                    }
                }

                1 -> {
                    if (hasActualData) {
                        if (MainViewerActivity.matchCache[matchNumber]!!.blueActualRPTwo != null && MainViewerActivity.matchCache[matchNumber]!!.blueActualRPTwo!!.toDouble() > Constants.PREDICTED_RANKING_POINT_QUALIFICATION) {
                            tv.setImageResource(R.drawable.pull_up_bars)
                            continue@blue_predicted
                        }
                    } else if (MainViewerActivity.matchCache[matchNumber]!!.bluePredictedRPTwo != null && MainViewerActivity.matchCache[matchNumber]!!.bluePredictedRPTwo!!.toDouble() > Constants.PREDICTED_RANKING_POINT_QUALIFICATION) {
                        tv.setImageResource(R.drawable.pull_up_bars)
                        continue@blue_predicted
                    }
                }
            }
            val value = getAllianceInMatchObjectByKey(
                Constants.PROCESSED_OBJECT.CALCULATED_PREDICTED_ALLIANCE_IN_MATCH.value,
                Constants.BLUE,
                matchNumber,
                field + "${
                    listOf(
                        viewHolder.tvBluePredictedRPOne, viewHolder.tvBluePredictedRPTwo
                    ).indexOf(tv) + 1
                }"
            )
            if (value != Constants.NULL_CHARACTER && value.toDouble() > Constants.PREDICTED_RANKING_POINT_QUALIFICATION) {
                when (listOf(viewHolder.tvBluePredictedRPOne, viewHolder.tvBluePredictedRPTwo).indexOf(tv)) {
                    0 -> {
                        if (hasActualData) {
                            MainViewerActivity.matchCache[matchNumber]!!.blueActualRPOne =
                                (("%.0f").format(value.toFloat())).toFloat()
                        } else {
                            MainViewerActivity.matchCache[matchNumber]!!.bluePredictedRPOne =
                                (("%.1f").format(value.toFloat())).toFloat()
                        }
                        tv.setImageResource(R.drawable.cargo_ball)
                    }

                    1 -> {
                        if (hasActualData) {
                            MainViewerActivity.matchCache[matchNumber]!!.blueActualRPTwo =
                                (("%.0f").format(value.toFloat())).toFloat()
                        } else {
                            MainViewerActivity.matchCache[matchNumber]!!.bluePredictedRPTwo =
                                (("%.1f").format(value.toFloat())).toFloat()
                        }
                        tv.setImageResource(R.drawable.pull_up_bars)
                    }
                }
            } else tv.setImageDrawable(null)
        }

        // Set background for starred matches
        if (MainViewerActivity.starredMatches.contains(matchNumber)) {
            viewHolder.wholeLine.setBackgroundColor(
                ContextCompat.getColor(context, if (hasActualData) R.color.DarkYellow else R.color.Yellow)
            )
        }

        // Set the click listeners to go to match details, etc.
        setClickListeners(rowView!!, viewHolder, position)

        return rowView
    }

    /**
     *  View holder class to handle the elements used in the custom cells.
     */
    private class ViewHolder(view: View?) {
        val tvMatchNumber = view?.findViewById(R.id.tv_match_number) as TextView
        val tvBluePredictedScore = view?.findViewById(R.id.tv_blue_predicted_score) as TextView
        val tvRedPredictedScore = view?.findViewById(R.id.tv_red_predicted_score) as TextView
        val tvBluePredictedRPOne = view?.findViewById(R.id.tv_blue_predicted_rp1) as ImageView
        val tvRedPredictedRPOne = view?.findViewById(R.id.tv_red_predicted_rp1) as ImageView
        val tvBluePredictedRPTwo = view?.findViewById(R.id.tv_blue_predicted_rp2) as ImageView
        val tvRedPredictedRPTwo = view?.findViewById(R.id.tv_red_predicted_rp2) as ImageView
        val btnBlueTeamOne = view?.findViewById(R.id.btn_blue_team_one) as Button
        val btnBlueTeamTwo = view?.findViewById(R.id.btn_blue_team_two) as Button
        val btnBlueTeamThree = view?.findViewById(R.id.btn_blue_team_three) as Button
        val btnRedTeamOne = view?.findViewById(R.id.btn_red_team_one) as Button
        val btnRedTeamTwo = view?.findViewById(R.id.btn_red_team_two) as Button
        val btnRedTeamThree = view?.findViewById(R.id.btn_red_team_three) as Button
        val wholeLine = view?.findViewById(R.id.whole_line) as LinearLayout

        /**
         * Convenience property holding all the blue teams.
         */
        val blueTeams = listOf(btnBlueTeamOne, btnBlueTeamTwo, btnBlueTeamThree)

        /**
         * Convenience property holding all the red teams.
         */
        val redTeams = listOf(btnRedTeamOne, btnRedTeamTwo, btnRedTeamThree)
    }

    /**
     * Sets the on click listeners and on long click listeners for the buttons in the cell.
     */
    private fun setClickListeners(rowView: View, viewHolder: ViewHolder, position: Int) {
        fun matchClick(it: View) {
            val matchDetailsFragment = MatchDetailsFragment()
            Log.d("data-refresh", "created MatchDetailsFragment in MatchSchedule")
            val matchDetailsFragmentTransaction = context.supportFragmentManager.beginTransaction()
            val matchSelected =
                if (scheduleType == Constants.ScheduleType.OUR_MATCHES || scheduleType == Constants.ScheduleType.STARRED_MATCHES) {
                    matchContents.keys.toList()[position].toInt()
                } else {
                    position + 1
                }
            matchDetailsFragment.arguments = Bundle().apply { putInt(Constants.MATCH_NUMBER, matchSelected) }
            matchDetailsFragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
            matchDetailsFragmentTransaction.addToBackStack(null).replace(
                (it.rootView.findViewById(R.id.nav_host_fragment) as ViewGroup).id, matchDetailsFragment
            ).commit()
        }

        fun teamLongClick(selected: Button, it: View) {
            val teamNumber = selected.text.toString()
            val matchScheduleFragment = MatchScheduleFragment()
            val matchScheduleFragmentTransaction = context.supportFragmentManager.beginTransaction()
            matchScheduleFragment.arguments = Bundle().apply { putString(Constants.TEAM_NUMBER, teamNumber) }
            matchScheduleFragmentTransaction.addToBackStack(null).replace(
                (it.rootView.findViewById(R.id.nav_host_fragment) as ViewGroup).id, matchScheduleFragment
            ).commit()
        }

        // When an item click occurs, go to the MatchDetails fragment of the match item clicked.
        rowView.setOnClickListener {
            matchClick(it)
        }
        for (team in viewHolder.blueTeams) {
            team.setOnClickListener {
                matchClick(it)
            }
            team.setOnLongClickListener {
                teamLongClick(team, it)
                return@setOnLongClickListener true
            }
        }
        for (team in viewHolder.redTeams) {
            team.setOnClickListener {
                matchClick(it)
            }
            team.setOnLongClickListener {
                teamLongClick(team, it)
                return@setOnLongClickListener true
            }
        }

        // Mark matches as starred when long clicked.
        rowView.setOnLongClickListener {
            if (MainViewerActivity.starredMatches.contains(viewHolder.tvMatchNumber.text.toString())) {
                // The match is already starred.
                MainViewerActivity.starredMatches.remove(viewHolder.tvMatchNumber.text.toString())
                listView.invalidateViews()
            } else {
                // The match is not starred.
                MainViewerActivity.starredMatches.add(viewHolder.tvMatchNumber.text.toString())
                listView.invalidateViews()
            }
            StarredMatches.input()
            return@setOnLongClickListener true
        }
    }

}
