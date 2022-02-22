/*
* MatchScheduleFragment.kt
* viewer
*
* Created on 1/26/2020
* Copyright 2020 Citrus Circuits. All rights reserved.
*/

package com.example.viewer_2022

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.viewer_2022.constants.Constants
import com.example.viewer_2022.fragments.match_schedule.MatchScheduleListAdapter
import com.example.viewer_2022.fragments.match_schedule.match_details.MatchDetailsFragment
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_match_schedule.view.*

//The fragment of the match schedule 'view' that is one of the options of the navigation bar.
open class MatchScheduleFragment : IFrag(){
    private var teamNumber: String? = null
    private var refreshId: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        arguments?.let {
            teamNumber = it.getString(Constants.TEAM_NUMBER, null)
        }
        val root = inflater.inflate(R.layout.fragment_match_schedule, container, false)

        updateMatchScheduleListView(root, Constants.ScheduleType.ALL_MATCHES)

        return root
    }

    fun updateMatchScheduleListView(root: View, scheduleType: Constants.ScheduleType) {
        val adapter = MatchScheduleListAdapter(
            activity!!,
            (getMatchSchedule(
                (if (scheduleType == Constants.ScheduleType.OUR_MATCHES) listOf(Constants.MY_TEAM_NUMBER) else listOf()),
                scheduleType == Constants.ScheduleType.STARRED_MATCHES
            )
                    ),
            scheduleType,
            root.lv_match_schedule
        )
        if(refreshId == null){
            refreshId = MainViewerActivity.refreshManager.addRefreshListener {
                Log.d("data-refresh", "Updated: match-schedule")
                adapter.notifyDataSetChanged()
            }
        }
        root.lv_match_schedule.adapter = adapter

        if(teamNumber != null){
            root.match_search_bar.setText(teamNumber)
            val search = listOf(teamNumber!!)
            var matchesWanted = getMatchSchedule(search,
                false
            )
            if(!matchesWanted.isEmpty()) {
                (adapter as MatchScheduleListAdapter).updateData(matchesWanted, Constants.ScheduleType.OUR_MATCHES)
                Log.e("matchesWanted", "$matchesWanted")
            } else if (teamNumber!!.length == 0){
                matchesWanted = getMatchSchedule((listOf()), true)
                (adapter as MatchScheduleListAdapter).updateData(matchesWanted, Constants.ScheduleType.STARRED_MATCHES)
            } else {
                (adapter as MatchScheduleListAdapter).updateData(matchesWanted, Constants.ScheduleType.OUR_MATCHES)
            }
        }

        root.match_search_bar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val search = mutableListOf(s.toString())
                if (scheduleType == Constants.ScheduleType.OUR_MATCHES) {
                    search.add(Constants.MY_TEAM_NUMBER)
                }
                var matchesWanted = getMatchSchedule(search,
                    scheduleType == Constants.ScheduleType.STARRED_MATCHES
                )
                if(!matchesWanted.isEmpty()) {
                    (adapter as MatchScheduleListAdapter).updateData(matchesWanted, Constants.ScheduleType.OUR_MATCHES)
                    Log.e("matchesWanted", "$matchesWanted")
                } else if (s.toString().length == 0){
                    matchesWanted = getMatchSchedule((if (scheduleType == Constants.ScheduleType.OUR_MATCHES) listOf(Constants.MY_TEAM_NUMBER) else listOf()), scheduleType == Constants.ScheduleType.STARRED_MATCHES)
                    (adapter as MatchScheduleListAdapter).updateData(matchesWanted, scheduleType)
                } else {
                     (adapter as MatchScheduleListAdapter).updateData(matchesWanted, Constants.ScheduleType.OUR_MATCHES)
                 }
            }
            override fun afterTextChanged(s: Editable) {}
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        MainViewerActivity.refreshManager.removeRefreshListener(refreshId)
    }
}