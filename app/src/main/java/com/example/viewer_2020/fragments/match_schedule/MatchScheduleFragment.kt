/*
* MatchScheduleFragment.kt
* viewer
*
* Created on 1/26/2020
* Copyright 2020 Citrus Circuits. All rights reserved.
*/

package com.example.viewer_2020

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.viewer_2020.constants.Constants
import com.example.viewer_2020.fragments.match_schedule.MatchScheduleListAdapter
import com.example.viewer_2020.constants.ScheduleType
import com.example.viewer_2020.fragments.match_schedule.match_details.MatchDetailsFragment
import kotlinx.android.synthetic.main.fragment_match_schedule.view.*
import kotlinx.android.synthetic.main.match_schedule_cell.view.*

//The fragment of the match schedule 'view' that is one of the options of the navigation bar.
open class MatchScheduleFragment : IFrag(){

    val matchDetailsFragment = MatchDetailsFragment()
    val matchDetailsFragmentArguments = Bundle()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_match_schedule, container, false)

        updateMatchScheduleListView(root, ScheduleType.ALL_MATCHES)

        val matchDetailsFragmentTransaction = this.fragmentManager!!.beginTransaction()

        // When an item click occurs, go to the MatchDetails fragment of the match item clicked.
        root.lv_match_schedule.setOnItemClickListener { _, _, position, _ ->
            matchDetailsFragmentArguments.putInt(Constants.MATCH_NUMBER, position + 1)
            matchDetailsFragment.arguments = matchDetailsFragmentArguments
            matchDetailsFragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
            matchDetailsFragmentTransaction.addToBackStack(null).replace(
                (view!!.parent as ViewGroup).id,
                matchDetailsFragment
            ).commit()
        }

        // Mark matches as starred when long clicked.
        root.lv_match_schedule.setOnItemLongClickListener { _, _, position, _ ->
            val cell = root.lv_match_schedule.adapter.getView(position, null, root.lv_match_schedule)
            if (MainViewerActivity.starredMatches.contains(cell.tv_match_number.text.toString())) {
                // The match is already starred.
                MainViewerActivity.starredMatches.remove(cell.tv_match_number.text.toString())
                root.lv_match_schedule.invalidateViews()
            } else {
                // The match is not starred.
                MainViewerActivity.starredMatches.add(cell.tv_match_number.text.toString())
                root.lv_match_schedule.invalidateViews()
            }
            context?.getSharedPreferences("VIEWER", 0)?.edit()
                ?.putStringSet("starredMatches", MainViewerActivity.starredMatches)?.apply()
            return@setOnItemLongClickListener true
        }
        return root
    }

    fun updateMatchScheduleListView(root: View, scheduleType: ScheduleType) {
        adapter = MatchScheduleListAdapter(
            activity!!,
            (getMatchSchedule(
                (if (scheduleType == ScheduleType.OUR_MATCHES) Constants.MY_TEAM_NUMBER else null),
                scheduleType == ScheduleType.STARRED_MATCHES
            )
                    ),
            scheduleType
        )
        root.lv_match_schedule.adapter = adapter
    }
}