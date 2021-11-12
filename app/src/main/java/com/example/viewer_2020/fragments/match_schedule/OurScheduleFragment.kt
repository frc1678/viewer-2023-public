package com.example.viewer_2020.fragments.match_schedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.viewer_2020.MatchScheduleFragment
import com.example.viewer_2020.R
import com.example.viewer_2020.constants.Constants
import com.example.viewer_2020.getMatchSchedule
import kotlinx.android.synthetic.main.fragment_match_schedule.*
import kotlinx.android.synthetic.main.fragment_match_schedule.view.*

class OurScheduleFragment : MatchScheduleFragment() {

    fun changeTeamNumber(num: String, view: View){
        updateMatchScheduleListView(view, num)
            view.lv_match_schedule.setOnItemClickListener { _, _, position, _ ->
                itemClick(position, num)
            }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_match_schedule, container, false)
        updateMatchScheduleListView(root, Constants.MY_TEAM_NUMBER)
        root.lv_match_schedule.setOnItemClickListener { _, _, position, _ ->
            itemClick(position, Constants.MY_TEAM_NUMBER)
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        btn_t1.isEnabled = true
        btn_t9.isEnabled = true
        btn_t10.isEnabled = true
        btn_t11.isEnabled = true

        btn_t1.setOnClickListener {
            changeTeamNumber("1678", view)
        }

        btn_t9.setOnClickListener {
            changeTeamNumber("9678", view)
        }

        btn_t10.setOnClickListener {
            changeTeamNumber("10678", view)
        }

        btn_t11.setOnClickListener {
            changeTeamNumber("11678", view)
        }
    }

    fun itemClick(pos: Int, teamNum: String){
        val matchDetailsFragmentTransaction = this.fragmentManager!!.beginTransaction()
        matchDetailsFragmentArguments.putInt(
            Constants.MATCH_NUMBER,
            getMatchSchedule(teamNum).keys.elementAt(pos).toInt())
        matchDetailsFragment.arguments = matchDetailsFragmentArguments
        matchDetailsFragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
        matchDetailsFragmentTransaction.addToBackStack(null).replace(
            (view!!.parent as ViewGroup).id,
            matchDetailsFragment
        ).commit()
    }
}