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
import com.example.viewer_2020.fragments.match_schedule.match_details.MatchDetailsFragment
import kotlinx.android.synthetic.main.fragment_match_schedule.view.*

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

        updateMatchScheduleListView(root, false)

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
        return root
    }

    fun updateMatchScheduleListView(root: View, ourSchedule: Boolean) {
        adapter = MatchScheduleListAdapter(
            activity!!,
            (getMatchSchedule((if(ourSchedule) Constants.MY_TEAM_NUMBER else null))
                    ),
            ourSchedule
        )
        root.lv_match_schedule.adapter =adapter
    }
}