/*
* MatchScheduleListAdapter.kt
* viewer
*
* Created on 1/26/2020
* Copyright 2020 Citrus Circuits. All rights reserved.
*/

package com.example.viewer_2020.fragments.match_schedule

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.viewer_2020.*
import com.example.viewer_2020.data.Match
import com.example.viewer_2020.constants.Constants
import com.example.viewer_2020.R
import com.example.viewer_2020.constants.ScheduleType
import kotlinx.android.synthetic.main.match_details_cell.view.*
import java.lang.Float.parseFloat

// Custom list adapter class with Match object handling to display the custom cell for the match schedule.
class MatchScheduleListAdapter(
    private val context: Context,
    private var matchContents: Map<String, Match>,
    private val scheduleType: ScheduleType
) : BaseAdapter() {



    private val inflater = LayoutInflater.from(context)

    // Return the size of the match schedule.
    override fun getCount(): Int {
        return matchContents.size
    }

    // Return the Match object given the match number.
    override fun getItem(position: Int): Match? {
        return when (scheduleType) {
            ScheduleType.OUR_MATCHES, ScheduleType.STARRED_MATCHES ->
                matchContents[matchContents.keys.toList()[position]]
            else -> matchContents[(position + 1).toString()]
        }
    }

    // Return the position of the cell.
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    // Populate the elements of the custom cell.
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var blueAct = false
        var redAct = false
        Log.e("matchContents", matchContents["1"]?.redTeams.toString())

        val viewHolder: ViewHolder
        val rowView: View?
        val matchNumber: String = when (scheduleType) {
            ScheduleType.OUR_MATCHES, ScheduleType.STARRED_MATCHES ->
                matchContents.keys.toList()[position]
            else -> (position + 1).toString()
        }


        if (convertView == null) {
            rowView = inflater.inflate(R.layout.match_schedule_cell, parent, false)
            viewHolder =
                ViewHolder(
                    rowView
                )
            rowView.tag = viewHolder
        } else {
            rowView = convertView
            viewHolder = rowView.tag as ViewHolder
        }

        for (tv in listOf(
            viewHolder.tvRedTeamOne,
            viewHolder.tvRedTeamTwo,
            viewHolder.tvRedTeamThree
        )) {

            tv.text = matchContents[matchNumber]!!.redTeams[listOf(
                viewHolder.tvRedTeamOne,
                viewHolder.tvRedTeamTwo,
                viewHolder.tvRedTeamThree
            ).indexOf(tv)]
        }
        for (tv in listOf(
            viewHolder.tvBlueTeamOne,
            viewHolder.tvBlueTeamTwo,
            viewHolder.tvBlueTeamThree
        )) {
            tv.text = matchContents[matchNumber]!!.blueTeams[0 +
                    listOf(
                        viewHolder.tvBlueTeamOne,
                        viewHolder.tvBlueTeamTwo,
                        viewHolder.tvBlueTeamThree
                    ).indexOf(tv)]
        }
        viewHolder.tvMatchNumber.text = matchNumber

        if (getAllianceInMatchObjectByKey(
                Constants.PROCESSED_OBJECT.CALCULATED_PREDICTED_ALLIANCE_IN_MATCH.value,
                Constants.RED, matchNumber.toString(),
                "has_actual_data").toBoolean()){
                    redAct = true
        }
        if (getAllianceInMatchObjectByKey(
                Constants.PROCESSED_OBJECT.CALCULATED_PREDICTED_ALLIANCE_IN_MATCH.value,
                Constants.BLUE, matchNumber.toString(),
                "has_actual_data").toBoolean()){
                    blueAct = true
        }
        val field : String

        if (redAct && blueAct) {
            field = "actual_rp"
            viewHolder.wholeLine.setBackgroundColor(ContextCompat.getColor(
                context,
                R.color.LightGray
            ))
        } else{
            field = "predicted_rp"
            viewHolder.wholeLine.setBackgroundColor(ContextCompat.getColor(
                context,
                R.color.White
            ))
        }

        if ((!blueAct or !redAct) && MainViewerActivity.matchCache[matchNumber]!!.bluePredictedScore != null) {
            viewHolder.tvBluePredictedScore.text =
                MainViewerActivity.matchCache[matchNumber]!!.bluePredictedScore.toString()
        } else if (blueAct && redAct && MainViewerActivity.matchCache[matchNumber]!!.blueActualScore != null){
            viewHolder.tvBluePredictedScore.text =
                MainViewerActivity.matchCache[matchNumber]!!.blueActualScore.toString()
        }
        else {
            val value = if (blueAct && redAct) {
                getAllianceInMatchObjectByKey(
                    Constants.PROCESSED_OBJECT.CALCULATED_PREDICTED_ALLIANCE_IN_MATCH.value,
                    Constants.BLUE, matchNumber, "actual_score"
                )
            } else{
                getAllianceInMatchObjectByKey(
                    Constants.PROCESSED_OBJECT.CALCULATED_PREDICTED_ALLIANCE_IN_MATCH.value,
                    Constants.BLUE, matchNumber, "predicted_score"
                )
            }
            if (value != Constants.NULL_CHARACTER) {
                viewHolder.tvBluePredictedScore.text =
                    parseFloat(("%.2f").format(value.toFloat())).toString()
                if(!blueAct or !redAct) {
                    MainViewerActivity.matchCache[matchNumber]!!.bluePredictedScore =
                        parseFloat(("%.2f").format(value.toFloat()))
                }else{
                    MainViewerActivity.matchCache[matchNumber]!!.blueActualScore =
                        parseFloat(("%.2f").format(value.toFloat()))
                }
            } else {
                viewHolder.tvBluePredictedScore.text =
                    Constants.NULL_PREDICTED_SCORE_CHARACTER
            }
        }

        if ((!redAct or !blueAct) && MainViewerActivity.matchCache[matchNumber]!!.redPredictedScore != null) {
            viewHolder.tvRedPredictedScore.text =
                MainViewerActivity.matchCache[matchNumber]!!.redPredictedScore.toString()
        }else if (redAct && blueAct && MainViewerActivity.matchCache[matchNumber]!!.redActualScore != null) {
            viewHolder.tvRedPredictedScore.text =
                MainViewerActivity.matchCache[matchNumber]!!.redActualScore.toString()
        } else {
            val value = if (redAct && blueAct) {
                getAllianceInMatchObjectByKey(
                    Constants.PROCESSED_OBJECT.CALCULATED_PREDICTED_ALLIANCE_IN_MATCH.value,
                    Constants.RED, matchNumber, "actual_score"
                )
            } else{
                getAllianceInMatchObjectByKey(
                    Constants.PROCESSED_OBJECT.CALCULATED_PREDICTED_ALLIANCE_IN_MATCH.value,
                    Constants.RED, matchNumber, "predicted_score"
                )
            }
            if (value != Constants.NULL_CHARACTER) {
                viewHolder.tvRedPredictedScore.text =
                    parseFloat(("%.2f").format(value.toFloat())).toString()
                if (!redAct or !blueAct) {
                    MainViewerActivity.matchCache[matchNumber]!!.redPredictedScore =
                        parseFloat(("%.2f").format(value.toFloat()))
                }else{
                    MainViewerActivity.matchCache[matchNumber]!!.redActualScore =
                        parseFloat(("%.2f").format(value.toFloat()))
                }
            } else {
                viewHolder.tvRedPredictedScore.text =
                    Constants.NULL_PREDICTED_SCORE_CHARACTER
            }
        }

        for (tv in listOf(viewHolder.tvBluePredictedScore, viewHolder.tvRedPredictedScore)) {
            if (tv.text == Constants.NULL_PREDICTED_SCORE_CHARACTER) {
                tv.setTextColor(
                    ContextCompat.getColor(
                        context,
                        R.color.ElectricGreen
                    )
                )
            } else {
                tv.setTextColor(
                    ContextCompat.getColor(
                        context,
                        R.color.Black
                    )
                )
            }
        }
        red_predicted@ for (tv in listOf(
            viewHolder.tvRedPredictedRPOne,
            viewHolder.tvRedPredictedRPTwo
        )) {
            when (listOf(
                viewHolder.tvRedPredictedRPOne,
                viewHolder.tvRedPredictedRPTwo
            ).indexOf(tv)) {
                0 -> {
                    if (redAct && blueAct) {
                        if (MainViewerActivity.matchCache[matchNumber]!!.redActualRPOne != null &&
                            MainViewerActivity.matchCache[matchNumber]!!.redActualRPOne!!.toDouble() >
                            Constants.PREDICTED_RANKING_POINT_QUALIFICATION
                        ) {
                            tv.setImageResource(R.drawable.cargo_ball)
                            continue@red_predicted
                        }
                    }else if (MainViewerActivity.matchCache[matchNumber]!!.redPredictedRPOne != null &&
                            MainViewerActivity.matchCache[matchNumber]!!.redPredictedRPOne!!.toDouble() >
                            Constants.PREDICTED_RANKING_POINT_QUALIFICATION
                        ) {
                        tv.setImageResource(R.drawable.cargo_ball)
                        continue@red_predicted
                    }
                }
                1 -> {
                    if (redAct && blueAct) {
                        if (MainViewerActivity.matchCache[matchNumber]!!.redActualRPTwo != null &&
                            MainViewerActivity.matchCache[matchNumber]!!.redActualRPTwo!!.toDouble() >
                            Constants.PREDICTED_RANKING_POINT_QUALIFICATION) {
                            tv.setImageResource(R.drawable.pull_up_bars)
                            continue@red_predicted
                        }
                    }
                    else if (MainViewerActivity.matchCache[matchNumber]!!.redPredictedRPTwo != null &&
                            MainViewerActivity.matchCache[matchNumber]!!.redPredictedRPTwo!!.toDouble() >
                            Constants.PREDICTED_RANKING_POINT_QUALIFICATION
                        ) {
                            tv.setImageResource(R.drawable.pull_up_bars)
                            continue@red_predicted
                        }
                }
            }
            val value = getAllianceInMatchObjectByKey(
                Constants.PROCESSED_OBJECT.CALCULATED_PREDICTED_ALLIANCE_IN_MATCH.value,
                Constants.RED, matchNumber, field +
                        "${listOf(
                            viewHolder.tvRedPredictedRPOne,
                            viewHolder.tvRedPredictedRPTwo
                        ).indexOf(tv) + 1}"
            )
            if (value != Constants.NULL_CHARACTER &&
                value.toDouble() > Constants.PREDICTED_RANKING_POINT_QUALIFICATION
            ) {
                when (listOf(
                    viewHolder.tvRedPredictedRPOne,
                    viewHolder.tvRedPredictedRPTwo
                ).indexOf(tv)) {
                    0 -> {
                        if(redAct && blueAct){
                            MainViewerActivity.matchCache[matchNumber]!!.redActualRPOne =
                                parseFloat(("%.2f").format(value.toFloat()))
                        }else{
                            MainViewerActivity.matchCache[matchNumber]!!.redPredictedRPOne =
                                parseFloat(("%.2f").format(value.toFloat()))
                        }
                    }
                    1 -> {
                        if(redAct && blueAct){
                            MainViewerActivity.matchCache[matchNumber]!!.redActualRPTwo =
                                parseFloat(("%.2f").format(value.toFloat()))
                        }else{
                            MainViewerActivity.matchCache[matchNumber]!!.redPredictedRPTwo =
                                parseFloat(("%.2f").format(value.toFloat()))
                        }
                    }
                }
            } else tv.setImageDrawable(null)
        }
        blue_predicted@ for (tv in listOf(
            viewHolder.tvBluePredictedRPOne,
            viewHolder.tvBluePredictedRPTwo
        )) {
            when (listOf(
                viewHolder.tvBluePredictedRPOne,
                viewHolder.tvBluePredictedRPTwo
            ).indexOf(tv)) {
                0 -> {
                    if (blueAct && redAct) {
                        if (MainViewerActivity.matchCache[matchNumber]!!.blueActualRPOne != null &&
                            MainViewerActivity.matchCache[matchNumber]!!.blueActualRPOne!!.toDouble() >
                            Constants.PREDICTED_RANKING_POINT_QUALIFICATION) {
                            tv.setImageResource(R.drawable.cargo_ball)
                            continue@blue_predicted
                        }
                    } else if (MainViewerActivity.matchCache[matchNumber]!!.bluePredictedRPOne != null &&
                            MainViewerActivity.matchCache[matchNumber]!!.bluePredictedRPOne!!.toDouble() >
                            Constants.PREDICTED_RANKING_POINT_QUALIFICATION
                        ) {
                            tv.setImageResource(R.drawable.cargo_ball)
                            continue@blue_predicted
                        }
                }
                1 -> {
                    if (blueAct && redAct) {
                        if (MainViewerActivity.matchCache[matchNumber]!!.blueActualRPTwo != null &&
                            MainViewerActivity.matchCache[matchNumber]!!.blueActualRPTwo!!.toDouble() >
                            Constants.PREDICTED_RANKING_POINT_QUALIFICATION
                        ) {
                            tv.setImageResource(R.drawable.pull_up_bars)
                            continue@blue_predicted
                        }
                    } else if (MainViewerActivity.matchCache[matchNumber]!!.bluePredictedRPTwo != null &&
                            MainViewerActivity.matchCache[matchNumber]!!.bluePredictedRPTwo!!.toDouble() >
                            Constants.PREDICTED_RANKING_POINT_QUALIFICATION
                        ) {
                            tv.setImageResource(R.drawable.pull_up_bars)
                            continue@blue_predicted
                        }
                }
            }
            val value = getAllianceInMatchObjectByKey(
                Constants.PROCESSED_OBJECT.CALCULATED_PREDICTED_ALLIANCE_IN_MATCH.value,
                Constants.BLUE, matchNumber, field +
                        "${listOf(
                            viewHolder.tvBluePredictedRPOne,
                            viewHolder.tvBluePredictedRPTwo
                        ).indexOf(tv) + 1}"
            )
            if (value != Constants.NULL_CHARACTER &&
                value.toDouble() > Constants.PREDICTED_RANKING_POINT_QUALIFICATION
            ) {
                when (listOf(
                    viewHolder.tvBluePredictedRPOne,
                    viewHolder.tvBluePredictedRPTwo
                ).indexOf(tv)) {
                    0 -> {
                        if (redAct && blueAct){
                            MainViewerActivity.matchCache[matchNumber]!!.blueActualRPOne =
                                parseFloat(("%.2f").format(value.toFloat()))
                        }else{
                            MainViewerActivity.matchCache[matchNumber]!!.bluePredictedRPOne =
                                parseFloat(("%.2f").format(value.toFloat()))
                        }
                    }
                    1 -> {
                        if(redAct && blueAct){
                            MainViewerActivity.matchCache[matchNumber]!!.blueActualRPTwo =
                                parseFloat(("%.2f").format(value.toFloat()))
                        }else{
                            MainViewerActivity.matchCache[matchNumber]!!.bluePredictedRPTwo =
                                parseFloat(("%.2f").format(value.toFloat()))
                        }
                    }
                }
            } else tv.setImageDrawable(null)
        }
        if (MainViewerActivity.starredMatches.contains(matchNumber)) {
            viewHolder.wholeLine.setBackgroundColor(ContextCompat.getColor(context, R.color.Yellow))
        }
        return rowView!!
    }

    // View holder class to handle the elements used in the custom cells.
    private class ViewHolder(view: View?) {
        val tvMatchNumber = view?.findViewById(R.id.tv_match_number) as TextView
        val tvBluePredictedScore = view?.findViewById(R.id.tv_blue_predicted_score) as TextView
        val tvRedPredictedScore = view?.findViewById(R.id.tv_red_predicted_score) as TextView
        val tvBluePredictedRPOne = view?.findViewById(R.id.tv_blue_predicted_rp1) as ImageView
        val tvRedPredictedRPOne = view?.findViewById(R.id.tv_red_predicted_rp1) as ImageView
        val tvBluePredictedRPTwo = view?.findViewById(R.id.tv_blue_predicted_rp2) as ImageView
        val tvRedPredictedRPTwo = view?.findViewById(R.id.tv_red_predicted_rp2) as ImageView
        val tvBlueTeamOne = view?.findViewById(R.id.tv_blue_team_one) as TextView
        val tvBlueTeamTwo = view?.findViewById(R.id.tv_blue_team_two) as TextView
        val tvBlueTeamThree = view?.findViewById(R.id.tv_blue_team_three) as TextView
        val tvRedTeamOne = view?.findViewById(R.id.tv_red_team_one) as TextView
        val tvRedTeamTwo = view?.findViewById(R.id.tv_red_team_two) as TextView
        val tvRedTeamThree = view?.findViewById(R.id.tv_red_team_three) as TextView
        val wholeLine = view?.findViewById(R.id.whole_line) as LinearLayout
    }
}
