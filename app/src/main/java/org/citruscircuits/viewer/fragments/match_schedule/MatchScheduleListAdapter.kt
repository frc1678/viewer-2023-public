package org.citruscircuits.viewer.fragments.match_schedule

import android.graphics.Typeface.DEFAULT
import android.graphics.Typeface.DEFAULT_BOLD
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import org.citruscircuits.viewer.MainViewerActivity
import org.citruscircuits.viewer.MainViewerActivity.StarredMatches
import org.citruscircuits.viewer.R
import org.citruscircuits.viewer.constants.Constants
import org.citruscircuits.viewer.data.Match
import org.citruscircuits.viewer.fragments.match_details.MatchDetailsFragment
import org.citruscircuits.viewer.getAllianceInMatchObjectByKey

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

    fun updateData(newMatchSchedule: Map<String, Match>, newScheduleType: Constants.ScheduleType) {
        matchContents = newMatchSchedule
        scheduleType = newScheduleType
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
        val matchNumber = matchContents.keys.toList()[position]

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
            Constants.RED,
            matchNumber,
            "has_actual_data"
        ).toBoolean() && getAllianceInMatchObjectByKey(
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

        // Set the border and styling for the winning alliance
        if (hasActualData) {
            if (getAllianceInMatchObjectByKey(
                    Constants.RED,
                    matchNumber,
                    "won_match"
                ).toBoolean()
            ) {
                viewHolder.imgRedWin.setImageResource(R.drawable.bg_border_small)
                viewHolder.imgBlueWin.setImageDrawable(null)
                for (team in viewHolder.redTeams) team.typeface = DEFAULT_BOLD
            } else {
                viewHolder.imgBlueWin.setImageResource(R.drawable.bg_border_small)
                viewHolder.imgRedWin.setImageDrawable(null)
                for (team in viewHolder.blueTeams) team.typeface = DEFAULT_BOLD
            }
        } else {
            viewHolder.imgRedWin.setImageDrawable(null)
            viewHolder.imgBlueWin.setImageDrawable(null)
        }

        val field = if (hasActualData) "actual_rp" else "predicted_rp"

        // Set the match status icon
        viewHolder.imgMatchStatus.setImageResource(
            if (hasActualData) R.drawable.ic_baseline_check_24 else R.drawable.ic_outline_pending_24
        )

        // Set the blue predicted score
        if ((!hasActualData) && MainViewerActivity.matchCache[matchNumber]!!.bluePredictedScore != null) {
            // Cache hit
            viewHolder.tvBlueScore.text =
                MainViewerActivity.matchCache[matchNumber]!!.bluePredictedScore.toString()
        } else if (hasActualData && MainViewerActivity.matchCache[matchNumber]!!.blueActualScore != null) {
            // Cache hit
            // noinspection SetTextI18n
            viewHolder.tvBlueScore.text =
                "%.0f".format(MainViewerActivity.matchCache[matchNumber]!!.blueActualScore)
        } else {
            // Cache miss
            val value = getAllianceInMatchObjectByKey(
                Constants.BLUE,
                matchNumber,
                if (hasActualData) "actual_score" else "predicted_score"
            )
            if (value != null) {
                viewHolder.tvBlueScore.text =
                    (if (hasActualData) "%.0f" else "%.1f").format(value.toFloat())
                if (!hasActualData) {
                    MainViewerActivity.matchCache[matchNumber]!!.bluePredictedScore =
                        "%.1f".format(value.toFloat()).toFloat()
                } else {
                    MainViewerActivity.matchCache[matchNumber]!!.blueActualScore =
                        "%.0f".format(value.toFloat()).toFloat()
                }
            } else {
                viewHolder.tvBlueScore.text = Constants.NULL_PREDICTED_SCORE_CHARACTER
            }
        }

        // Set the red predicted score
        if ((!hasActualData) && MainViewerActivity.matchCache[matchNumber]!!.redPredictedScore != null) {
            // Cache hit
            viewHolder.tvRedScore.text =
                MainViewerActivity.matchCache[matchNumber]!!.redPredictedScore.toString()
        } else if (hasActualData && MainViewerActivity.matchCache[matchNumber]!!.redActualScore != null) {
            // Cache hit
            // noinspection SetTextI18n
            viewHolder.tvRedScore.text =
                "%.0f".format(MainViewerActivity.matchCache[matchNumber]!!.redActualScore)
        } else {
            // Cache miss
            val value = if (hasActualData) {
                getAllianceInMatchObjectByKey(
                    Constants.RED,
                    matchNumber,
                    "actual_score"
                )
            } else {
                getAllianceInMatchObjectByKey(
                    Constants.RED,
                    matchNumber,
                    "predicted_score"
                )
            }
            if (value != null) {
                viewHolder.tvRedScore.text =
                    (if (hasActualData) "%.0f" else "%.1f").format(value.toFloat())
                if (!hasActualData) {
                    MainViewerActivity.matchCache[matchNumber]!!.redPredictedScore =
                        "%.1f".format(value.toFloat()).toFloat()
                } else {
                    MainViewerActivity.matchCache[matchNumber]!!.redActualScore =
                        "%.0f".format(value.toFloat()).toFloat()
                }
            } else {
                viewHolder.tvRedScore.text = Constants.NULL_PREDICTED_SCORE_CHARACTER
            }
        }

        // Use a different color when there's no predicted score
        for (tv in listOf(viewHolder.tvBlueScore, viewHolder.tvRedScore)) {
            if (tv.text == Constants.NULL_PREDICTED_SCORE_CHARACTER) {
                tv.setTextColor(ContextCompat.getColor(context, R.color.ElectricGreen))
            } else {
                tv.setTextColor(ContextCompat.getColor(context, R.color.Black))
            }
        }

        // Set the ranking point icons
        red_predicted@ for ((rp, tv) in mapOf(
            1 to viewHolder.imgRedRpOne,
            2 to viewHolder.imgRedRpTwo
        )) {
            // Check the cache to see if the RP values have already been cached
            when (rp) {
                1 -> {
                    if (hasActualData) {
                        if (MainViewerActivity.matchCache[matchNumber]!!.redActualRPOne != null) {
                            if (MainViewerActivity.matchCache[matchNumber]!!.redActualRPOne!!.toDouble() > Constants.PREDICTED_RANKING_POINT_QUALIFICATION) {
                                tv.setImageResource(R.drawable.baseline_battery_charging_full)
                            } else {
                                tv.setImageDrawable(null)
                            }
                            continue@red_predicted
                        }
                    } else if (MainViewerActivity.matchCache[matchNumber]!!.redPredictedRPOne != null) {
                        if (MainViewerActivity.matchCache[matchNumber]!!.redPredictedRPOne!!.toDouble() > Constants.PREDICTED_RANKING_POINT_QUALIFICATION) {
                            tv.setImageResource(R.drawable.baseline_battery_charging_full)
                        } else {
                            tv.setImageDrawable(null)
                        }
                        continue@red_predicted
                    }
                }

                2 -> {
                    if (hasActualData) {
                        if (MainViewerActivity.matchCache[matchNumber]!!.redActualRPTwo != null) {
                            if (MainViewerActivity.matchCache[matchNumber]!!.redActualRPTwo!!.toDouble() > Constants.PREDICTED_RANKING_POINT_QUALIFICATION) {
                                tv.setImageResource(R.drawable.ic_link)
                            } else {
                                tv.setImageDrawable(null)
                            }
                            continue@red_predicted
                        }
                    } else if (MainViewerActivity.matchCache[matchNumber]!!.redPredictedRPTwo != null) {
                        if (MainViewerActivity.matchCache[matchNumber]!!.redPredictedRPTwo!!.toDouble() > Constants.PREDICTED_RANKING_POINT_QUALIFICATION) {
                            tv.setImageResource(R.drawable.ic_link)
                        } else {
                            tv.setImageDrawable(null)
                        }
                        continue@red_predicted
                    }
                }
            }
            // Cache missed, so we need to retrieve from the database
            val value = getAllianceInMatchObjectByKey(
                Constants.RED,
                matchNumber,
                field + "$rp"
            )
            if (value != null && value.toDouble() > Constants.PREDICTED_RANKING_POINT_QUALIFICATION) {
                when (rp) {
                    1 -> {
                        if (hasActualData) {
                            MainViewerActivity.matchCache[matchNumber]!!.redActualRPOne =
                                "%.0f".format(value.toFloat()).toFloat()
                        } else {
                            MainViewerActivity.matchCache[matchNumber]!!.redPredictedRPOne =
                                "%.1f".format(value.toFloat()).toFloat()
                        }
                        tv.setImageResource(R.drawable.baseline_battery_charging_full)
                    }

                    2 -> {
                        if (hasActualData) {
                            MainViewerActivity.matchCache[matchNumber]!!.redActualRPTwo =
                                "%.0f".format(value.toFloat()).toFloat()
                        } else {
                            MainViewerActivity.matchCache[matchNumber]!!.redPredictedRPTwo =
                                "%.1f".format(value.toFloat()).toFloat()
                        }
                        tv.setImageResource(R.drawable.ic_link)
                    }
                }
            } else tv.setImageDrawable(null)
        }
        blue_predicted@ for ((rp, tv) in mapOf(
            1 to viewHolder.imgBlueRpOne,
            2 to viewHolder.imgBlueRpTwo
        )) {
            // Check the cache to see if the RP values have already been cached
            when (rp) {
                1 -> {
                    if (hasActualData) {
                        if (MainViewerActivity.matchCache[matchNumber]!!.blueActualRPOne != null) {
                            if (MainViewerActivity.matchCache[matchNumber]!!.blueActualRPOne!!.toDouble() > Constants.PREDICTED_RANKING_POINT_QUALIFICATION) {
                                tv.setImageResource(R.drawable.baseline_battery_charging_full)
                            } else {
                                tv.setImageDrawable(null)
                            }
                            continue@blue_predicted
                        }
                    } else if (MainViewerActivity.matchCache[matchNumber]!!.bluePredictedRPOne != null) {
                        if (MainViewerActivity.matchCache[matchNumber]!!.bluePredictedRPOne!!.toDouble() > Constants.PREDICTED_RANKING_POINT_QUALIFICATION) {
                            tv.setImageResource(R.drawable.baseline_battery_charging_full)
                        } else {
                            tv.setImageDrawable(null)
                        }
                        continue@blue_predicted
                    }
                }

                2 -> {
                    if (hasActualData) {
                        if (MainViewerActivity.matchCache[matchNumber]!!.blueActualRPTwo != null) {
                            if (MainViewerActivity.matchCache[matchNumber]!!.blueActualRPTwo!!.toDouble() > Constants.PREDICTED_RANKING_POINT_QUALIFICATION) {
                                tv.setImageResource(R.drawable.ic_link)
                            } else {
                                tv.setImageDrawable(null)
                            }
                            continue@blue_predicted
                        }
                    } else if (MainViewerActivity.matchCache[matchNumber]!!.bluePredictedRPTwo != null) {
                        if (MainViewerActivity.matchCache[matchNumber]!!.bluePredictedRPTwo!!.toDouble() > Constants.PREDICTED_RANKING_POINT_QUALIFICATION) {
                            tv.setImageResource(R.drawable.ic_link)
                        } else {
                            tv.setImageDrawable(null)
                        }
                        continue@blue_predicted
                    }
                }
            }
            // Cache missed, so we need to retrieve from the database
            val value = getAllianceInMatchObjectByKey(
                Constants.BLUE,
                matchNumber,
                field + "$rp"
            )
            if (value != null && value.toDouble() > Constants.PREDICTED_RANKING_POINT_QUALIFICATION) {
                when (rp) {
                    1 -> {
                        if (hasActualData) {
                            MainViewerActivity.matchCache[matchNumber]!!.blueActualRPOne =
                                "%.0f".format(value.toFloat()).toFloat()
                        } else {
                            MainViewerActivity.matchCache[matchNumber]!!.bluePredictedRPOne =
                                "%.1f".format(value.toFloat()).toFloat()
                        }
                        tv.setImageResource(R.drawable.baseline_battery_charging_full)
                    }

                    2 -> {
                        if (hasActualData) {
                            MainViewerActivity.matchCache[matchNumber]!!.blueActualRPTwo =
                                "%.0f".format(value.toFloat()).toFloat()
                        } else {
                            MainViewerActivity.matchCache[matchNumber]!!.bluePredictedRPTwo =
                                "%.1f".format(value.toFloat()).toFloat()
                        }
                        tv.setImageResource(R.drawable.ic_link)
                    }
                }
            } else tv.setImageDrawable(null)
        }

        // Set star for starred matches
        if (MainViewerActivity.starredMatches.contains(matchNumber)) {
            viewHolder.starIcon.setImageResource(R.drawable.ic_baseline_star_border_24)
        } else {
            viewHolder.starIcon.setImageDrawable(null)
        }

        // Set the background color based on the number of starred teams in the match
        // Note: This will automatically break once it gets past 1, since we don't care about any more than that.
        var starredTeamCount = 0
        for (team in matchContents[matchNumber]!!.blueTeams + matchContents[matchNumber]!!.redTeams) {
            if (MainViewerActivity.StarredTeams.contains(team)) {
                starredTeamCount++
                if (starredTeamCount > 1) break
            }
        }
        viewHolder.wholeCell.setBackgroundColor(
            ContextCompat.getColor(
                context, when (starredTeamCount) {
                    0 -> R.color.Highlight_0
                    1 -> R.color.Highlight_1
                    else -> R.color.Highlight_2
                }
            )
        )

        // Override the background color if our team is in the match
        if ((matchContents[matchNumber]!!.blueTeams + matchContents[matchNumber]!!.redTeams).contains(
                Constants.MY_TEAM_NUMBER
            )
        ) viewHolder.wholeCell.setBackgroundColor(
            ContextCompat.getColor(
                context,
                R.color.LimeGreen
            )
        )

        // Set the click listeners to go to match details, etc.
        setClickListeners(rowView!!, viewHolder, position)
        return rowView
    }

    /**
     *  View holder class to handle the elements used in the custom cells.
     */
    private class ViewHolder(view: View?) {
        val tvMatchNumber = view?.findViewById(R.id.tv_match_number) as TextView
        val imgMatchStatus = view?.findViewById(R.id.match_status) as ImageView
        val tvBlueScore = view?.findViewById(R.id.blue_score) as TextView
        val tvRedScore = view?.findViewById(R.id.red_score) as TextView
        val imgBlueWin = view?.findViewById(R.id.blue_win) as ImageView
        val imgRedWin = view?.findViewById(R.id.red_win) as ImageView
        val imgBlueRpOne = view?.findViewById(R.id.blue_rp1) as ImageView
        val imgRedRpOne = view?.findViewById(R.id.red_rp1) as ImageView
        val imgBlueRpTwo = view?.findViewById(R.id.blue_rp2) as ImageView
        val imgRedRpTwo = view?.findViewById(R.id.red_rp2) as ImageView
        val tvBlueTeamOne = view?.findViewById(R.id.blue_team1) as TextView
        val tvBlueTeamTwo = view?.findViewById(R.id.blue_team2) as TextView
        val tvBlueTeamThree = view?.findViewById(R.id.blue_team3) as TextView
        val tvRedTeamOne = view?.findViewById(R.id.red_team1) as TextView
        val tvRedTeamTwo = view?.findViewById(R.id.red_team2) as TextView
        val tvRedTeamThree = view?.findViewById(R.id.red_team3) as TextView
        val wholeCell = view?.findViewById(R.id.whole_cell) as ConstraintLayout
        val starIcon = view?.findViewById(R.id.star_icon) as ImageView

        /**
         * Convenience property holding all the blue teams.
         */
        val blueTeams = listOf(tvBlueTeamOne, tvBlueTeamTwo, tvBlueTeamThree)

        /**
         * Convenience property holding all the red teams.
         */
        val redTeams = listOf(tvRedTeamOne, tvRedTeamTwo, tvRedTeamThree)
    }

    /**
     * Sets the on click listeners and on long click listeners for the buttons in the cell.
     */
    private fun setClickListeners(rowView: View, viewHolder: ViewHolder, position: Int) {
        fun matchClick(it: View) {
            val matchDetailsFragment = MatchDetailsFragment()
            Log.d("data-refresh", "created MatchDetailsFragment in MatchSchedule")
            val matchDetailsFragmentTransaction = context.supportFragmentManager.beginTransaction()
            matchDetailsFragment.arguments = Bundle().apply {
                putInt(Constants.MATCH_NUMBER, matchContents.keys.toList()[position].toInt())
            }
            matchDetailsFragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
            matchDetailsFragmentTransaction.addToBackStack(null).replace(
                (it.rootView.findViewById(R.id.nav_host_fragment) as ViewGroup).id,
                matchDetailsFragment
            ).commit()
        }

        fun teamLongClick(selected: TextView, it: View) {
            val teamNumber = selected.text.toString()
            val matchScheduleFragment = MatchScheduleFragment()
            val matchScheduleFragmentTransaction = context.supportFragmentManager.beginTransaction()
            matchScheduleFragment.arguments =
                Bundle().apply { putString(Constants.TEAM_NUMBER, teamNumber) }
            matchScheduleFragmentTransaction.addToBackStack(null).replace(
                (it.rootView.findViewById(R.id.nav_host_fragment) as ViewGroup).id,
                matchScheduleFragment
            ).commit()
        }

        // When an item click occurs, go to the MatchDetails fragment of the match item clicked.
        rowView.setOnClickListener {
            matchClick(it)
        }
        for (team in viewHolder.blueTeams union viewHolder.redTeams) {
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