/*
* RankingFragment.kt
* viewer
*
* Created on 1/26/2020
* Copyright 2020 Citrus Circuits. All rights reserved.
*/

package org.citruscircuits.viewer.fragments.ranking

import android.annotation.SuppressLint
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
import org.citruscircuits.viewer.convertToFilteredTeamsList
import org.citruscircuits.viewer.fragments.team_details.TeamDetailsFragment
import kotlinx.android.synthetic.main.fragment_ranking.view.*


// The fragment of the ranking lists 'view' that is one of the options of the navigation bar.
// Disclaimer: This fragment contains another menu bar which is displayed directly above the
// main menu bar. This navigation/menu bar does not switch between fragments on each menu's selection
// like the main menu bar does. This navigation bar only receives the position/ID of the menu selected
// and then updated the adapter of the list view that is right above it.
class RankingFragment : Fragment() {

    private val teamDetailsFragment = TeamDetailsFragment()
    private val teamDetailsFragmentArguments = Bundle()

    private var refreshId: String? = null


    @SuppressLint("ResourceAsColor")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Usage of counter variable to increment at every iteration of the menu item forEach statement.
        // Used to find the index of the current selected menu in the menu item list because the
        // MenuItem object does not have an indexOf() return function.
        val root = inflater.inflate(R.layout.fragment_ranking, container, false)
        // This is a listener for the navigation bar onClick.
        // When a menu item of the navigation bar is clicked, it sends the MenuItem to
        // the handler activity (MainViewerActivity) to set the adapter of this fragment's
        // resource layout to the correct adapter given the new menu item that should be displayed.
        context?.resources?.let { root.btn_switch_rankings.setBackgroundColor(it.getColor(R.color.ToggleColor)) }
        context?.resources?.let { root.btn_switch_pred_rankings.setBackgroundColor(it.getColor(R.color.LightGray)) }

        root.tv_datapoint_two.text =
            Translations.ACTUAL_TO_HUMAN_READABLE[Constants.FIELDS_TO_BE_DISPLAYED_RANKING[1]]
        root.tv_datapoint_three.text =
            Translations.ACTUAL_TO_HUMAN_READABLE[Constants.FIELDS_TO_BE_DISPLAYED_RANKING[2]]
        root.tv_datapoint_four.text =
            Translations.ACTUAL_TO_HUMAN_READABLE[Constants.FIELDS_TO_BE_DISPLAYED_RANKING[3]]
        root.tv_datapoint_five.text =
            Translations.ACTUAL_TO_HUMAN_READABLE[Constants.FIELDS_TO_BE_DISPLAYED_RANKING[4]]
        val adapter = RankingListAdapter(
            requireActivity(), convertToFilteredTeamsList(
                MainViewerActivity.teamList
            )
        )
        if (refreshId == null) {
            refreshId = MainViewerActivity.refreshManager.addRefreshListener {
                Log.d("data-refresh", "Updated: ranking")
                adapter.notifyDataSetChanged()
            }
        }
        root.lv_ranking.adapter = adapter

        root.lv_ranking.setOnItemClickListener { _, _, position, _ ->
            val rankingFragmentTransaction = this.requireFragmentManager().beginTransaction()
            teamDetailsFragmentArguments.putString(
                Constants.TEAM_NUMBER, convertToFilteredTeamsList(
                    MainViewerActivity.teamList
                )[position]
            )
            teamDetailsFragmentArguments.putBoolean("LFM", false)
            teamDetailsFragment.arguments = teamDetailsFragmentArguments
            rankingFragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
            rankingFragmentTransaction.addToBackStack(null).replace(
                (requireView().parent as ViewGroup).id,
                teamDetailsFragment
            ).commit()
        }

        root.btn_switch_pred_rankings.setOnClickListener {
            toggleToPredicted()
        }
        root.btn_switch_rankings.setOnClickListener {
            toggleToPredicted()
        }
        return root
    }


    private fun toggleToPredicted() {
        val predictedRankingFragment = PredRankingFragment()
        val ft = requireFragmentManager().beginTransaction()
        if (requireFragmentManager().fragments.last().tag != "predRankings") ft.addToBackStack(null)
        ft.replace(R.id.nav_host_fragment, predictedRankingFragment, "predRankings")
            .commit()
        return
    }

    override fun onDestroy() {
        super.onDestroy()
        MainViewerActivity.refreshManager.removeRefreshListener(refreshId)
    }
}