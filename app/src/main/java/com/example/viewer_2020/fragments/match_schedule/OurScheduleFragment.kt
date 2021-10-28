package com.example.viewer_2020.fragments.match_schedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.viewer_2020.MatchScheduleFragment
import com.example.viewer_2020.R
import com.example.viewer_2020.constants.Constants
import com.example.viewer_2020.getMatchSchedule
import kotlinx.android.synthetic.main.fragment_match_schedule.view.*

class OurScheduleFragment : MatchScheduleFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_match_schedule, container, false)
        updateMatchScheduleListView(root, true)
        val matchDetailsFragmentTransaction = this.fragmentManager!!.beginTransaction()
        root.lv_match_schedule.setOnItemClickListener { _, _, position, _ ->
            matchDetailsFragmentArguments.putInt(
                Constants.MATCH_NUMBER,
                getMatchSchedule(Constants.MY_TEAM_NUMBER).keys.elementAt(position).toInt())
//                csvFile.filter { it.matches(Regex(".*[B|R]-${Constants.MY_TEAM_NUMBER}( .*)?")) }
//                    .sortedBy { it.trim().split(" ")[0].toInt() } [position]
//                    .trim().split(" ")[0].toInt()
            matchDetailsFragment.arguments = matchDetailsFragmentArguments
            matchDetailsFragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
            matchDetailsFragmentTransaction.addToBackStack(null).replace(
                (view!!.parent as ViewGroup).id,
                matchDetailsFragment
            ).commit()
        }
        return root
    }
}