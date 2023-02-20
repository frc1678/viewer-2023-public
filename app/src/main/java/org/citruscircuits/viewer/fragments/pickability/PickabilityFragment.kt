package org.citruscircuits.viewer.fragments.pickability

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import org.citruscircuits.viewer.MainViewerActivity
import org.citruscircuits.viewer.R
import org.citruscircuits.viewer.constants.Constants
import org.citruscircuits.viewer.convertToFilteredTeamsList
import org.citruscircuits.viewer.fragments.team_details.TeamDetailsFragment
import org.citruscircuits.viewer.getTeamDataValue
import kotlinx.android.synthetic.main.fragment_pickability.view.*
import org.citruscircuits.viewer.databinding.FragmentGraphsBinding
import org.citruscircuits.viewer.databinding.FragmentPickabilityBinding
import java.util.*

/**
 * Page that ranks the pickability of each team. Previously allowed for first pickability and second pickability
 */
class PickabilityFragment() : Fragment() {

    private val teamDetailsFragment = TeamDetailsFragment()
    private val teamDetailsFragmentArguments = Bundle()

    private var refreshId: String? = null

    private var mode = PickabilityMode.First
    private var _binding: FragmentPickabilityBinding? = null

    /**
     * This property is only valid between [onCreateView] and [onDestroyView].
     */
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPickabilityBinding.inflate(inflater, container, false)
        val map: Map<String, String> = updateMatchScheduleListView()

        binding.lvPickability.setOnItemClickListener { _, _, position, _ ->
            val list: List<String> = map.keys.toList()
            val pickabilityFragmentTransaction = parentFragmentManager.beginTransaction()
            teamDetailsFragmentArguments.putString(
                Constants.TEAM_NUMBER,
                list[position]
            )
            teamDetailsFragment.arguments = teamDetailsFragmentArguments
            pickabilityFragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
            pickabilityFragmentTransaction.addToBackStack(null).replace(
                (requireView().parent as ViewGroup).id,
                teamDetailsFragment
            ).commit()
        }

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.pickability,
            R.layout.gray_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.gray_spinner_item)
            binding.spinnerMode.adapter = adapter
        }
        binding.spinnerMode.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                PickabilityMode.fromSpinner(position)?.let {
                    mode = it
                    updateMatchScheduleListView()
                }

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

        return binding.root
    }


    private fun updateMatchScheduleListView(): Map<String, String> {
        val map = makeData()
        val adapter = PickabilityListAdapter(
            context = requireActivity(),
            items = map
        )

        if (refreshId == null) {
            refreshId = MainViewerActivity.refreshManager.addRefreshListener {
                Log.d("data-refresh", "Updated: Pickability")
                adapter.items = makeData()
                adapter.notifyDataSetChanged()
            }
        }

        binding.lvPickability.adapter = adapter
        return map
    }

    fun makeData(): Map<String, String> {

        var map = mutableMapOf<String, String>()
        val rawTeamNumbers = convertToFilteredTeamsList(
            MainViewerActivity.teamList
        )

        rawTeamNumbers.forEach { e ->
            map[e] = getTeamDataValue(
                e,
                mode.datapoint
            ) ?: Constants.NULL_CHARACTER
        }

        map = map.toList().sortedBy { (k, v) ->
            (v.toFloatOrNull())
        }.reversed().toMap().toMutableMap()
        return map.toMap()
    }

    override fun onDestroy() {
        super.onDestroy()
        MainViewerActivity.refreshManager.removeRefreshListener(refreshId)
    }
}

enum class PickabilityMode {
    First,
    SecondOffensive,
    SecondDefensive,
    SecondOverall;

    val datapoint: String
        get() = when (this) {
            First -> "first_pickability"
            SecondOffensive -> "offensive_second_pickability"
            SecondDefensive -> "defensive_second_pickability"
            SecondOverall -> "overall_second_pickability"
        }

    companion object {
        fun fromSpinner(spinnerPosition: Int): PickabilityMode? = when (spinnerPosition) {
            0 -> First
            1 -> SecondOverall
            2 -> SecondOffensive
            3 -> SecondDefensive
            else -> null
        }
    }
}
