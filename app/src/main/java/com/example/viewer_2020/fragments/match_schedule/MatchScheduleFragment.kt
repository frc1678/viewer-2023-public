/*
* MatchScheduleFragment.kt
* viewer
*
* Created on 1/26/2020
* Copyright 2020 Citrus Circuits. All rights reserved.
*/

package com.example.viewer_2020

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.viewer_2020.constants.Constants
import com.example.viewer_2020.data.Match
import com.example.viewer_2020.fragments.match_schedule.MatchScheduleListAdapter
import com.example.viewer_2020.fragments.match_schedule.match_details.MatchDetailsFragment
import kotlinx.android.synthetic.main.fragment_match_schedule.view.*
import kotlinx.android.synthetic.main.team_details.view.*
import android.util.Log

//The fragment of the match schedule 'view' that is one of the options of the navigation bar.
open class MatchScheduleFragment : Fragment() {

    val matchDetailsFragment = MatchDetailsFragment()
    val matchDetailsFragmentArguments = Bundle()
    var csvFile : MutableList<String> = csvFileRead("match_schedule.csv", false)

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
        root.lv_match_schedule.adapter =
            MatchScheduleListAdapter(
                activity!!,
                (convertMatchScheduleListToMap(
                    csvFile,
                    isFiltered = false,
                    matchNumber = null
                )!!
                        ),
                ourSchedule
            )
    }
}