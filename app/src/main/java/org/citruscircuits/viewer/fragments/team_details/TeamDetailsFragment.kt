/*
* TeamDetailsFragment.kt
* viewer
*
* Created on 2/19/2020
* Copyright 2020 Citrus Circuits. All rights reserved.
*/

package org.citruscircuits.viewer.fragments.team_details

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import org.citruscircuits.viewer.MainViewerActivity
import org.citruscircuits.viewer.R
import org.citruscircuits.viewer.constants.Constants
import org.citruscircuits.viewer.getTeamName
import kotlinx.android.synthetic.main.team_details.view.*
import org.citruscircuits.viewer.constants.Translations
import java.io.File

// The fragment class for the Team Details display that occurs when you click on a
// team in the match details page.
class TeamDetailsFragment : Fragment() {
    private var teamNumber: String? = null
    private var teamName: String? = null

    private var lfm: Boolean = false

    private var refreshId: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        val root = inflater.inflate(R.layout.team_details, container, false)
        populateTeamDetailsEssentials(root)
        updateDatapointDisplayListView(root)
        robotPics(root)

        /*
            This creates the on menu select listener for the TeamDetails fragment navigation bar.
          The purpose of this navigation bar is to switch between the type of data that the
          list view in team details displays. The list view adapter contents can be altered
          in Constants.kt -> FIELDS_TO_BE_DISPLAYED_TEAM_DETAILS. FIELDS_TO_BE_DISPLAYED_TEAM_DETAILS
          is a map of a string key to arraylist<string> value, with each string key being the menu
          items and the contents of each arraylist<string> being the specific data points displayed
          in each of the menu item's adapter settings sections.
         */
        return root
    }


    // Prepare the TeamDetails page by populating each text view and other XML element
    // with its team specific information.
    private fun populateTeamDetailsEssentials(root: View) {
        // If a fragment intent (bundle arguments) exists from the previous activity (MainViewerActivity),
        // then set the team number display on TeamDetails to the team number provided with the intent.

        // If the team number from the MainViewerActivity's match schedule list view cell position
        // is null, the default display will show '0' for the team number on TeamDetails.
        arguments?.let {
            teamNumber = it.getString(Constants.TEAM_NUMBER, Constants.NULL_CHARACTER)
            teamName = getTeamName(teamNumber!!)
            lfm = it.getBoolean("LFM")
        }
        root.tv_team_number.text = teamNumber.toString()
        root.tv_team_name.text = teamName ?: Constants.NULL_CHARACTER
    }

    // gets the lfm equivalent of each of the given datapoints
    private fun getLFMEquivalent(datapointList: List<String>): List<String> {
        var lfmDatapoints: MutableList<String> = mutableListOf()
        for (datapoint in datapointList) {
            if (!Constants.CATEGORY_NAMES.contains(datapoint) && !Constants.TEAM_AND_LFM_SHARED_DATAPOINTS.contains(datapoint)) {
                lfmDatapoints.add("lfm_$datapoint")
            }
            else if (Constants.TEAM_AND_LFM_SHARED_DATAPOINTS.contains(datapoint)) {
                lfmDatapoints.add(datapoint)
            }
            else {
                lfmDatapoints.add(Translations.TEAM_TO_LFM_HEADERS[datapoint]?: datapoint)
            }
        }
        return lfmDatapoints
    }

    // Updates the adapter for the list view of each team in the match details display.
    private fun updateDatapointDisplayListView(root: View) {
        // We set the adapter for their list view according to
        // the team number and the current section. We also include a list of the
        // data points we expect to be displayed on the TeamDetails list view.
        var user = MainViewerActivity.UserDatapoints.contents?.get("selected")?.asString
        var datapoints: MutableList<String> = mutableListOf()
        var userdatapoints = MainViewerActivity.UserDatapoints.contents?.get(user)?.asJsonArray
        if (userdatapoints != null) {
            for (i in userdatapoints) {
                if (Constants.FIELDS_TO_BE_DISPLAYED_TEAM_DETAILS.contains(i.asString)) {
                    datapoints.add(i.asString)
                }
            }
        }
        if (lfm) {
            root.btn_lfm.text = getString(R.string.to_all_matches)
            root.btn_lfm.textSize = 12F
        } else {
            root.btn_lfm.text = getString(R.string.to_last_four_matches)
            root.btn_lfm.textSize = 16F
        }
        val adapter = TeamDetailsAdapter(
            context = requireActivity(),
            datapointsDisplayed = if (lfm) getLFMEquivalent(datapoints) else datapoints,
            teamNumber = teamNumber!!,
            visualDataBar = ("Visual Data Bars" in (if (lfm) getLFMEquivalent(datapoints) else datapoints))
        )
        if (refreshId == null) {
            refreshId = MainViewerActivity.refreshManager.addRefreshListener {
                Log.d("data-refresh", "Updated: team-details")
                adapter.notifyDataSetChanged()
            }
        }
        root.lv_datapoint_display.adapter = adapter
// Repopulates the list view based on whether LFM is toggled or not
        root.btn_lfm.setOnClickListener {
            lfm = !lfm
            if (lfm) {
                root.btn_lfm.text = getString(R.string.to_all_matches)
                root.btn_lfm.textSize = 12F
            } else {
                root.btn_lfm.text = getString(R.string.to_last_four_matches)
                root.btn_lfm.textSize = 16F
            }
            this.arguments?.putBoolean("LFM", lfm)
            val adapter = TeamDetailsAdapter(
                context = requireActivity(),
                datapointsDisplayed = if (lfm) getLFMEquivalent(datapoints) else datapoints,
                teamNumber = teamNumber!!,
                visualDataBar = ("Visual Data Bars" in (if (lfm) getLFMEquivalent(datapoints) else datapoints))
            )
            root.lv_datapoint_display.adapter = adapter
        }
        root.btn_auto_paths.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.fade_in, R.anim.fade_out).addToBackStack(null)
                .replace((requireView().parent as ViewGroup).id, AutoPathsFragment().apply {
                    arguments = bundleOf(Constants.TEAM_NUMBER to teamNumber)
                }).commit()
        }
    }

    // Displays a button to view the team's picture if the picture file exists on the phone
    private fun robotPics(root: View) {
        val robotPicFragmentArguments = Bundle()
        val robotPicFragment = RobotPicFragment()
        if (!File(
                Constants.DOWNLOADS_FOLDER,
                "${teamNumber}_full_robot.jpg"
            ).exists() &&
            !File(
                Constants.DOWNLOADS_FOLDER,
                "${teamNumber}_front.jpg"
            ).exists() &&
            !File(
                Constants.DOWNLOADS_FOLDER,
                "${teamNumber}_side.jpg"
            ).exists()
        ) {
            root.robot_pic_button.layoutParams = LinearLayout.LayoutParams(0, 0, 0f)
            root.tv_team_number.gravity = Gravity.CENTER_HORIZONTAL
            root.tv_team_name.gravity = Gravity.CENTER_HORIZONTAL
        } else {
            root.robot_pic_button.setOnClickListener {
                robotPicFragmentArguments.putString(Constants.TEAM_NUMBER, teamNumber)
                robotPicFragment.arguments = robotPicFragmentArguments
                requireActivity().supportFragmentManager.beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.nav_host_fragment, robotPicFragment, "robot_pic")
                    .commit()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        MainViewerActivity.refreshManager.removeRefreshListener(refreshId)
    }
}