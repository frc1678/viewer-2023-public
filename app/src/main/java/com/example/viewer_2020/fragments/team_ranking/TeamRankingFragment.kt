package com.example.viewer_2020.fragments.team_ranking

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.viewer_2020.MainViewerActivity
import com.example.viewer_2020.R
import com.example.viewer_2020.constants.Constants
import com.example.viewer_2020.constants.Translations
import com.example.viewer_2020.fragments.team_details.TeamDetailsFragment
import com.example.viewer_2020.getTeamDataValue
import kotlinx.android.synthetic.main.fragment_team_ranking.*
import kotlinx.android.synthetic.main.fragment_team_ranking.view.*
import kotlinx.android.synthetic.main.team_ranking_cell.view.*


class TeamRankingFragment : Fragment() {
    companion object {
        const val TEAM_NUMBER = "teamNumber"
        const val DATA_POINT = "dataPoint"
    }


    var dataPoint: String? = null
    var teamNumber: String? = null

    var lvAdapter: TeamRankingListAdapter? = null

    private var refreshId: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_team_ranking, container, false)

        populateArguments(root)
        setTextViews(root)
        setupAdapter(root)

        return root
    }

    private fun populateArguments(root: View) {
        arguments?.let {
            dataPoint = it.getString(DATA_POINT, Constants.NULL_CHARACTER)
            teamNumber = it.getString(TEAM_NUMBER, Constants.NULL_CHARACTER)
        }
    }

    private fun setTextViews(root: View) {
        if (Translations.ACTUAL_TO_HUMAN_READABLE.containsKey(dataPoint)) {
            root.tv_datapoint_header.text = Translations.ACTUAL_TO_HUMAN_READABLE[dataPoint]
        } else {
            root.tv_datapoint_header.text = dataPoint
        }
    }

    private fun setupAdapter(root: View) {


        lvAdapter = TeamRankingListAdapter(activity!!, teamNumber, getData(descending = Constants.RANKABLE_FIELDS[dataPoint!!]!!))
        if(refreshId == null){
            refreshId = MainViewerActivity.refreshManager.addRefreshListener {
                Log.d("data-refresh", "Updated: team-ranking")
                lvAdapter?.updateItems(getData(descending = Constants.RANKABLE_FIELDS[dataPoint!!]!!))

            }
        }
        root.lv_team_ranking.adapter = lvAdapter
        root.lv_team_ranking.setOnItemClickListener { parent, view, position, id ->
            val teamDetailsFragmentTransaction = this.fragmentManager!!.beginTransaction()
            val teamDetailsFragment = TeamDetailsFragment()
            val teamDetailsFragmentArguments = Bundle()
            teamDetailsFragmentArguments.putString(
                Constants.TEAM_NUMBER,
                view.tv_team_number_ranking.text.toString()
            )
            teamDetailsFragment.arguments = teamDetailsFragmentArguments
            teamDetailsFragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
            teamDetailsFragmentTransaction.addToBackStack(null).replace(
                (root.parent as ViewGroup).id,
                teamDetailsFragment
            ).commit()
        }
    }

    private fun getData(descending: Boolean): List<TeamRankingItem> {
        val data = mutableListOf<TeamRankingItem>()

        MainViewerActivity.teamList.forEach {
            val value = getTeamDataValue(it, dataPoint!!).toFloatOrNull()
            data.add(
                TeamRankingItem(
                    it,
                    value?.let { it1 -> floatToString(it1) } ?: Constants.NULL_CHARACTER))
        }

        val nullTeams = data.filter { item -> item.value == Constants.NULL_CHARACTER }
        val dataTeams = data.filter { item -> item.value != Constants.NULL_CHARACTER }

        var sortedData = dataTeams.sortedWith(compareBy { it.value.toFloatOrNull() })

        if (descending) sortedData = sortedData.reversed()

        return sortedData + nullTeams
    }

    override fun onDestroy() {
        super.onDestroy()
        MainViewerActivity.refreshManager.removeRefreshListener(refreshId)
    }
}

data class TeamRankingItem(val teamNumber: String, val value: String)

//Maybe a better method of sorting https://kotlinlang.org/docs/collection-ordering.html
/*
 : Comparable<TeamRankingItem>{
    override fun compareTo(other: TeamRankingItem): Int = when {
        this.value.toFloatOrNull() != null && other.value.toFloatOrNull() != null && this.value.toFloat() != other.value.toFloat() -> this.value.toFloat() compareTo other.value.toFloat()
        else -> 0
    }
}
 */