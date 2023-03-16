/*
* MatchScheduleFragment.kt
* viewer
*
* Created on 1/26/2020
* Copyright 2020 Citrus Circuits. All rights reserved.
*/

package org.citruscircuits.viewer.fragments.match_schedule

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import org.citruscircuits.viewer.MainViewerActivity
import org.citruscircuits.viewer.R
import org.citruscircuits.viewer.constants.Constants
import org.citruscircuits.viewer.fragments.team_details.TeamDetailsFragment
import org.citruscircuits.viewer.getMatchSchedule
import kotlinx.android.synthetic.main.fragment_match_schedule.view.*

/**
 * The fragment of the match schedule 'view' that is one of the options of the navigation bar.
 */
class MatchScheduleFragment : Fragment() {

    private var refreshId: String? = null

    private lateinit var adapter: MatchScheduleListAdapter

    /**
     * The current schedule type, either all matches, our matches, or starred matches.
     * The user can set this using the dropdown.
     * The match schedule adapter is automatically updated when a new value is set to this variable.
     */
    private var scheduleType = Constants.ScheduleType.ALL_MATCHES
        set(value) {
            field = value
            updateAdapter(value, search)
        }

    /**
     * The team that is currently being searched.
     * The match schedule adapter is automatically updated when a new value is set to this variable.
     */
    private var search: String? = null
        set(value) {
            field = value?.uppercase()
            updateAdapter(scheduleType, field)
        }

    /**
     * Sends a request to the match schedule adapter to update with the new given data.
     */
    private fun updateAdapter(scheduleType: Constants.ScheduleType, search: String?) {
        val teams = mutableListOf<String>()
        if (scheduleType == Constants.ScheduleType.OUR_MATCHES) teams += Constants.MY_TEAM_NUMBER
        if (search != null) teams += search
        adapter.updateData(
            getMatchSchedule(teams, scheduleType == Constants.ScheduleType.STARRED_MATCHES),
            scheduleType
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the match schedule layout
        val root = inflater.inflate(R.layout.fragment_match_schedule, container, false)

        // Initialize the match schedule adapter and search bar listeners
        initializeMatchSchedule(root)

        // If a team number is passed in through a bundle, make it the current search query
        val team = arguments?.getString(Constants.TEAM_NUMBER, null)
        if (team != null) {
            search = team
            root.match_search_bar.setText(search)
        }

        // Initialize the match schedule filtering menu
        initializeSpinner(root)

        return root
    }

    /**
     * Initializes the match schedule filtering spinner with the entries and listeners.
     */
    private fun initializeSpinner(root: View) {
        val spinnerAdapter = ArrayAdapter.createFromResource(
            requireContext(), R.array.match_schedule_filter_array, android.R.layout.simple_spinner_item
        )
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        root.filter_spinner.adapter = spinnerAdapter
        root.filter_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                scheduleType = when (position) {
                    1 -> Constants.ScheduleType.OUR_MATCHES
                    2 -> Constants.ScheduleType.STARRED_MATCHES
                    else -> Constants.ScheduleType.ALL_MATCHES
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    /**
     * Initializes the match schedule adapter and the listeners for the search bar.
     */
    private fun initializeMatchSchedule(root: View) {
        // Set the adapter for the match schedule
        adapter = MatchScheduleListAdapter(
            requireActivity(), getMatchSchedule(
                if (scheduleType == Constants.ScheduleType.OUR_MATCHES) listOf(Constants.MY_TEAM_NUMBER) else emptyList(),
                scheduleType == Constants.ScheduleType.STARRED_MATCHES
            ), scheduleType, root.lv_match_schedule
        )
        root.lv_match_schedule.adapter = adapter
        // Refresh the match schedule when the app refreshes
        if (refreshId == null) {
            refreshId = MainViewerActivity.refreshManager.addRefreshListener {
                Log.d("data-refresh", "Updated: match-schedule")
                adapter.notifyDataSetChanged()
            }
        }

        // Change the searched team when the user edits the search text
        root.match_search_bar.addTextChangedListener(object : TextWatcher {
            val regex = "[^A-Z0-9]".toRegex()
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                if (s.isNotEmpty()) {
                    if (s.toString().contains(regex)) {
                        val tempString: String = s.toString()
                        root.match_search_bar.setText(regex.replace(tempString, ""))
                    }
                }
                search = if (root.match_search_bar.text.isEmpty()) null else root.match_search_bar.text.toString()
            }
        })

        // Open the team details for the searched team when 'enter' is pressed
        root.match_search_bar.setOnEditorActionListener { _, _, _ ->
            // If the searched team isn't valid, don't do anything
            if (!MainViewerActivity.teamList.contains(search)) {
                return@setOnEditorActionListener true
            }
            // Otherwise, go to the team details fragment
            requireFragmentManager().beginTransaction().addToBackStack(null)
                .replace(R.id.nav_host_fragment, TeamDetailsFragment().apply {
                    // Put the team number into the arguments for the team details fragment to use
                    arguments = Bundle().apply { putString(Constants.TEAM_NUMBER, search) }.apply { putBoolean("LFM", false) }
                }).commit()
            // Hide the keyboard once the new fragment has been created
            (requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
                root.match_search_bar.windowToken, 0
            )
            // Return true to say that the 'enter' action has been handled
            return@setOnEditorActionListener true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        MainViewerActivity.refreshManager.removeRefreshListener(refreshId)
    }
}
