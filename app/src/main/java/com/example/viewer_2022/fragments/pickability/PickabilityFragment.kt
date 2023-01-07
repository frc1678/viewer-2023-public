package com.example.viewer_2022.fragments.pickability

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.viewer_2022.MainViewerActivity
import com.example.viewer_2022.R
import com.example.viewer_2022.constants.Constants
import com.example.viewer_2022.convertToFilteredTeamsList
import com.example.viewer_2022.fragments.team_details.TeamDetailsFragment
import com.example.viewer_2022.getTeamDataValue
import kotlinx.android.synthetic.main.fragment_pickability.view.*
import java.util.*

/**
 * Page that ranks the pickability of each team. Previously allowed for first pickability and second pickability
 */
class PickabilityFragment(val mode: PickabilityMode) : Fragment() {

    private val teamDetailsFragment = TeamDetailsFragment()
    private val teamDetailsFragmentArguments = Bundle()

    private var refreshId: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_pickability, container, false)
        root.tv_pickability_header.text =
            mode.toString().lowercase(Locale.getDefault())
                .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() } + " Pickability"
        val map: Map<String, Float?> = updateMatchScheduleListView(root)

        if (mode == PickabilityMode.SECOND) {
            root.btn_pickability.text = " To First Pickability"
        } else root.btn_pickability.text = " To Second Pickability"
        root.lv_pickability.setOnItemClickListener { _, _, position, _ ->
            val list: List<String> = map.keys.toList()
            val pickabilityFragmentTransaction = this.fragmentManager!!.beginTransaction()
            teamDetailsFragmentArguments.putString(
                Constants.TEAM_NUMBER,
                list[position]
            )
            teamDetailsFragment.arguments = teamDetailsFragmentArguments
            pickabilityFragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
            pickabilityFragmentTransaction.addToBackStack(null).replace(
                (view!!.parent as ViewGroup).id,
                teamDetailsFragment
            ).commit()
        }


        root.btn_pickability.setOnClickListener {


            val secondpickabilityFragment = SecondPickabilityFragment(PickabilityMode.SECOND)
            val ft = fragmentManager!!.beginTransaction()
            if (fragmentManager!!.fragments.last().tag != "secondpickabilityRankings") ft.addToBackStack(
                null
            )
            ft.replace(
                R.id.nav_host_fragment,
                secondpickabilityFragment,
                "secondpickabilityRankings"
            )
                .commit()


        }

        return root
    }


    private fun updateMatchScheduleListView(root: View): Map<String, Float?> {
        val map = makeData()
        val adapter = PickabilityListAdapter(
            context = activity!!,
            items = map
        )

        if (refreshId == null) {
            refreshId = MainViewerActivity.refreshManager.addRefreshListener {
                Log.d("data-refresh", "Updated: Pickability")
                adapter.items = makeData()
                adapter.notifyDataSetChanged()
            }
        }

        root.lv_pickability.adapter = adapter
        return map
    }

    fun makeData(): Map<String, Float?> {

        var map = mutableMapOf<String, Float?>()
        val rawTeamNumbers = convertToFilteredTeamsList(
            MainViewerActivity.teamList
        )

        rawTeamNumbers.forEach { e ->
            map[e] = try {
                getTeamDataValue(
                    e,
                    (if (mode == PickabilityMode.FIRST) "first_pickability" else "second_pickability")
                )?.toFloat()
            } catch (e: Exception) {
                (-1000).toFloat()
            }
        }

        map = map.toList().sortedBy { (k, v) ->


            (v)
        }.reversed().toMap().toMutableMap()
        return map.toMap()
    }

    override fun onDestroy() {
        super.onDestroy()
        MainViewerActivity.refreshManager.removeRefreshListener(refreshId)
    }
}

enum class PickabilityMode {
    FIRST,
    SECOND
}