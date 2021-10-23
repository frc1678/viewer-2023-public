package com.example.viewer_2020.fragments.pickability

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.viewer_2020.R
import com.example.viewer_2020.constants.Constants
import com.example.viewer_2020.convertToFilteredTeamsList
import com.example.viewer_2020.csvFileRead
import com.example.viewer_2020.fragments.team_details.TeamDetailsFragment
import com.example.viewer_2020.getTeamDataValue
import kotlinx.android.synthetic.main.fragment_pickability.view.*
import kotlinx.android.synthetic.main.fragment_ranking.view.*
import java.lang.ClassCastException
import java.util.Comparator

class PickabilityFragment(val mode: PickabilityMode) : Fragment() {
    private val teamDetailsFragment = TeamDetailsFragment()
    private val teamDetailsFragmentArguments = Bundle()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_pickability, container, false)
        root.tv_pickability_header.text = mode.toString().toLowerCase().capitalize() + " Pickability"
        val map : Map<String, String> = updateMatchScheduleListView(root)

        root.lv_pickability.setOnItemClickListener { _, _, position, _ ->
            val list : List<String> = map.keys.toList()
            val pickabilityFragmentTransaction = this.fragmentManager!!.beginTransaction()
            teamDetailsFragmentArguments.putString(Constants.TEAM_NUMBER,
                list[position]
            )
            teamDetailsFragment.arguments = teamDetailsFragmentArguments
            pickabilityFragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
            pickabilityFragmentTransaction.addToBackStack(null).replace(
                (view!!.parent as ViewGroup).id,
                teamDetailsFragment
            ).commit()
        }

        return root
    }

    private fun updateMatchScheduleListView(root: View) : Map<String, String>{
        var map = mutableMapOf<String, String>()
        val rawTeamNumbers = convertToFilteredTeamsList(
            Constants.PROCESSED_OBJECT.CALCULATED_PREDICTED_TEAM.value,
            csvFileRead("team_list.csv", false)[0].trim().split(" ")
        )

        rawTeamNumbers.forEach { e -> map[e] = try {
            getTeamDataValue(
                e,
                (if (mode == PickabilityMode.FIRST) "first_pickability" else "second_pickability")
            )
        } catch (e: ClassCastException) {
            Constants.NULL_CHARACTER
        } }

        map = map.toList().sortedBy {(k, v) ->


            (if(v == Constants.NULL_CHARACTER) "-1" else v)
        }.reversed().toMap().toMutableMap()

        root.lv_pickability.adapter = PickabilityListAdapter(
            items = map,
            context = activity!!,
            mode = mode
        )
        return map
    }
}
enum class PickabilityMode {
    FIRST,
    SECOND
}