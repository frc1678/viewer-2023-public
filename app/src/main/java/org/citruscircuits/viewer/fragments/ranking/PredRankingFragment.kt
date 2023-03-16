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
import org.citruscircuits.viewer.convertToPredFilteredTeamsList
import org.citruscircuits.viewer.fragments.team_details.TeamDetailsFragment
import kotlinx.android.synthetic.main.fragment_ranking.view.*

/**
 * Page for showing predicted rankings
 */
class PredRankingFragment : Fragment() {
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
        context?.resources?.let { root.btn_switch_rankings.setBackgroundColor(it.getColor(R.color.LightGray)) }
        context?.resources?.let { root.btn_switch_pred_rankings.setBackgroundColor(it.getColor(R.color.ToggleColor)) }

        root.tv_datapoint_two.text =
            Translations.ACTUAL_TO_HUMAN_READABLE[Constants.FIELDS_TO_BE_DISPLAYED_RANKING[1]]
        root.tv_datapoint_three.text =
            Translations.ACTUAL_TO_HUMAN_READABLE[Constants.FIELDS_TO_BE_DISPLAYED_RANKING[2]]
        root.tv_datapoint_four.text =
            Translations.ACTUAL_TO_HUMAN_READABLE[Constants.FIELDS_TO_BE_DISPLAYED_RANKING[3]]
        root.tv_datapoint_five.text =
            Translations.ACTUAL_TO_HUMAN_READABLE[Constants.FIELDS_TO_BE_DISPLAYED_RANKING[0]]

        val adapter = PredRankingListAdapter(
            requireActivity(), convertToPredFilteredTeamsList(
                MainViewerActivity.teamList
            )
        )
        if (refreshId == null) {
            refreshId = MainViewerActivity.refreshManager.addRefreshListener {
                Log.d("data-refresh", "Updated: pred-ranking")
                adapter.notifyDataSetChanged()
            }
        }
        root.lv_ranking.adapter = adapter

        root.lv_ranking.setOnItemClickListener { _, _, position, _ ->
            val rankingFragmentTransaction = this.requireFragmentManager().beginTransaction()
            teamDetailsFragmentArguments.putString(
                Constants.TEAM_NUMBER, convertToPredFilteredTeamsList(
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
            toggleToRanking()
        }
        root.btn_switch_rankings.setOnClickListener {
            toggleToRanking()
        }
        return root
    }

    private fun toggleToRanking() {
        val rankingFragment = RankingFragment()
        requireFragmentManager().beginTransaction()
            .addToBackStack(null)
            .replace(R.id.nav_host_fragment, rankingFragment, "rankings")
            .commit()
        return
    }

    override fun onDestroy() {
        super.onDestroy()
        MainViewerActivity.refreshManager.removeRefreshListener(refreshId)
    }
}