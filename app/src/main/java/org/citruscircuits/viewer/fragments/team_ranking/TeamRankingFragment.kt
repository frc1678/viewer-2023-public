package org.citruscircuits.viewer.fragments.team_ranking

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.citruscircuits.viewer.MainViewerActivity
import org.citruscircuits.viewer.R
import org.citruscircuits.viewer.constants.Constants
import org.citruscircuits.viewer.constants.Translations
import org.citruscircuits.viewer.fragments.team_details.TeamDetailsFragment
import org.citruscircuits.viewer.getRankingList
import kotlinx.android.synthetic.main.fragment_team_ranking.view.*
import kotlinx.android.synthetic.main.team_ranking_cell.view.*

/**
 * Fragment that shows the ranking of a specific datapoint
 */
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
            if (Constants.FIELDS_TO_BE_DISPLAYED_LFM.contains(dataPoint) and !Constants.FIELDS_TO_BE_DISPLAYED_TEAM_DETAILS.contains(dataPoint)) {
                root.tv_datapoint_header.text =
                    "L4M " + Translations.ACTUAL_TO_HUMAN_READABLE[dataPoint]
            } else {
                root.tv_datapoint_header.text = Translations.ACTUAL_TO_HUMAN_READABLE[dataPoint]
            }
        } else {
            root.tv_datapoint_header.text = dataPoint
        }
    }

    private fun setupAdapter(root: View) {
        lvAdapter = TeamRankingListAdapter(
            requireActivity(),
            teamNumber,
            dataPoint!!,
            getRankingList(
                datapoint = dataPoint!!
            ),
            dataPoint in Constants.PIT_DATA
        )
        if (refreshId == null) {
            refreshId = MainViewerActivity.refreshManager.addRefreshListener {
                Log.d("data-refresh", "Updated: team-ranking")
                lvAdapter?.updateItems(
                    getRankingList(
                        datapoint = dataPoint!!
                    )
                )

            }
        }
        root.lv_team_ranking.adapter = lvAdapter
        root.lv_team_ranking.setOnItemClickListener { parent, view, position, id ->
            val teamDetailsFragmentTransaction = this.requireFragmentManager().beginTransaction()
            val teamDetailsFragment = TeamDetailsFragment()
            val teamDetailsFragmentArguments = Bundle()
            teamDetailsFragmentArguments.putString(
                Constants.TEAM_NUMBER,
                view.tv_team_number_ranking.text.toString()
            )
            teamDetailsFragmentArguments.putBoolean("LFM", false)
            teamDetailsFragment.arguments = teamDetailsFragmentArguments
            teamDetailsFragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
            teamDetailsFragmentTransaction.addToBackStack(null).replace(
                (root.parent as ViewGroup).id,
                teamDetailsFragment
            ).commit()
        }
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