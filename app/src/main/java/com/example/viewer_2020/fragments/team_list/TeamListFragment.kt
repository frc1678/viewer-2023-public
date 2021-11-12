package com.example.viewer_2020.fragments.team_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.fragment.app.Fragment
import com.example.viewer_2020.*
import com.example.viewer_2020.constants.Constants
import com.example.viewer_2020.fragments.team_details.TeamDetailsFragment
import kotlinx.android.synthetic.main.fragment_team_list.view.*

// displays the team numbers in team list in numerical order
class TeamListFragment() : IFrag() {
    private val teamDetailsFragment = TeamDetailsFragment()
    private val teamDetailsFragmentArguments = Bundle()
    private val list: List<String> = MainViewerActivity.teamList.sortedBy{it.toInt()}


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_team_list, container, false)

        root.lv_team_list.setOnItemClickListener { _, _, position, _ ->
            val teamListFragmentTransaction = this.fragmentManager!!.beginTransaction()
            teamDetailsFragmentArguments.putString(
                Constants.TEAM_NUMBER,
                list[position]
            )
            teamDetailsFragment.arguments = teamDetailsFragmentArguments
            teamListFragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
            teamListFragmentTransaction.addToBackStack(null).replace(
                (view!!.parent as ViewGroup).id,
                teamDetailsFragment
            ).commit()
        }

        updateTeamListView(root)

        return root
    }

    private fun updateTeamListView(root: View){
        adapter = TeamListAdapter(
            context = activity!!,
            items = list
        )
        root.lv_team_list.adapter = adapter
    }


}
