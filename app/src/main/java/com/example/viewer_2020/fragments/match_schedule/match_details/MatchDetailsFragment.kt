/*
* MatchDetailsFragment.kt
* viewer
*
* Created on 2/10/2020
* Copyright 2020 Citrus Circuits. All rights reserved.
*/

package com.example.viewer_2020.fragments.match_schedule.match_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.viewer_2020.*
import com.example.viewer_2020.constants.Constants
import com.example.viewer_2020.constants.Translations
import com.example.viewer_2020.fragments.team_details.TeamDetailsFragment
import kotlinx.android.synthetic.main.match_details.*
import kotlinx.android.synthetic.main.match_details.view.*
import java.lang.Float.parseFloat

// The fragment class for the Match Details display that occurs when you click on a
// match in the match schedule page.
class MatchDetailsFragment : Fragment() {
    private var matchNumber: Int? = null

    private val teamDetailsFragment = TeamDetailsFragment()
    private val teamDetailsFragmentArguments = Bundle()
    lateinit var headerDisplay: List<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        arguments?.let {
            matchNumber = it.getInt(Constants.MATCH_NUMBER, 0)
        }

        headerDisplay = (if (getAllianceInMatchObjectByKey(
                Constants.PROCESSED_OBJECT.CALCULATED_PREDICTED_ALLIANCE_IN_MATCH.value,
                Constants.BLUE, matchNumber.toString(),
                "has_actual_data").toBoolean() and getAllianceInMatchObjectByKey(
                Constants.PROCESSED_OBJECT.CALCULATED_PREDICTED_ALLIANCE_IN_MATCH.value,
                Constants.RED, matchNumber.toString(),
                "has_actual_data").toBoolean()) Constants.FIELDS_TO_BE_DISPLAYED_MATCH_DETAILS_HEADER_PLAYED else Constants.FIELDS_TO_BE_DISPLAYED_MATCH_DETAILS_HEADER_NOT_PLAYED)

        val root = inflater.inflate(R.layout.match_details, container, false)

        populateMatchDetailsEssentials(root)

        // in Constants.kt -> FIELDS_TO_BE_DISPLAYED_MATCH_DETAILS. FIELDS_TO_BE_DISPLAYED_MATCH_DETAILS
        // is a list of strings

        for (teamNumber in getTeamNumbersXML(root)) {
            // If the index of the team number in the team number list is below 3, it means that the
            // team number is a team from the blue alliance.

            // The teamNumber.text prompt receives a string value, so we pull the filtered match schedule
            // that only contains the single match that is requested using the 'matchNumber' prompt, and
            // then we get the value of the matchNumber key of the single-match filtered match schedule map.

            // After getting the correct Match object value from the filtered match schedule map, we
            // set the teamNumber text to the correct team index of the blueTeams/redTeams list of the Match object.
            // We get the index by checking which index of the teamNumber collection list the current team
            // number iteration is.
            if (getTeamNumbersXML(root).indexOf(teamNumber) < 3) {
                teamNumber.text = getMatchSchedule()[matchNumber.toString()]!!.blueTeams[getTeamNumbersXML(root).indexOf(teamNumber)]
            } else {
                teamNumber.text = getMatchSchedule()[matchNumber.toString()]!!.redTeams[getTeamNumbersXML(root).indexOf(teamNumber) - 3]
            }

            // We run this method because the code above sets each team number text view to the
            // specified team number, and both the updateTeamListViews method and
            // the initTeamNumberClickListener methods pull the text view's text
            // value to access each team number.
            updateTeamListViews(root)
            initTeamNumberClickListeners(root)
        }
        return root
    }

    // Returns each of the six team's team number xml elements.
    private fun getTeamNumbersXML(root: View): List<TextView> {
        return listOf<TextView>(root.tv_team_one_label, root.tv_team_two_label, root.tv_team_three_label,
            root.tv_team_four_label, root.tv_team_five_label, root.tv_team_six_label)
    }

    //Returns all of the team numbers in a match as a list of Strings
    private fun getTeamNumbersList (root: View): List<String> {
        return listOf<String>(root.tv_team_one_label.text.toString(), root.tv_team_two_label.text.toString(), root.tv_team_three_label.text.toString(),
            root.tv_team_four_label.text.toString(), root.tv_team_five_label.text.toString(), root.tv_team_six_label.text.toString())
    }

    // Returns each of the three match details header text views that lay beside the team number.
    private fun getHeaderCollection(root: View): List<TextView> {
        return listOf<TextView>(root.tv_header_one, root.tv_header_two, root.tv_header_three,
            root.tv_header_four, root.tv_header_five, root.tv_header_six)
    }

    private fun getHeaderLabelCollection(root: View): List<TextView> {
        return listOf<TextView>(root.tv_header_label_one, root.tv_header_label_two,
            root.tv_header_label_three, root.tv_header_label_four, root.tv_header_label_five,
            root.tv_header_label_six)
    }

    // On every team number's specified text view, when the user clicks on the text view it will
    // then go to a new TeamDetails page for the given team number.
    private fun initTeamNumberClickListeners(root: View) {
        val matchDetailsFragmentTransaction = this.fragmentManager!!.beginTransaction()
        for (tv in getTeamNumbersXML(root)) {
            tv.setOnClickListener {
                teamDetailsFragmentArguments.putString(Constants.TEAM_NUMBER, tv.text.toString())
                teamDetailsFragment.arguments = teamDetailsFragmentArguments
                matchDetailsFragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                matchDetailsFragmentTransaction.addToBackStack(null).replace(
                    (view!!.parent as ViewGroup).id,
                    teamDetailsFragment
                ).commit()
            }
        }
    }

    // Updates the adapter for the list view of each team in the match details display.
    private fun updateTeamListViews(root: View) {
        // For every team in the match details, we set the adapter for their list view according to
        // their team number and the current type Match object. We also include a list of the
        // data points we expect to be displayed on the MatchDetails list view.
//        for (listView in getListViewCollection(root)) {
        val datapointsDisplay = (if (getAllianceInMatchObjectByKey(
                Constants.PROCESSED_OBJECT.CALCULATED_PREDICTED_ALLIANCE_IN_MATCH.value,
                Constants.BLUE, matchNumber.toString(),
                "has_actual_data").toBoolean() and (getAllianceInMatchObjectByKey(
                Constants.PROCESSED_OBJECT.CALCULATED_PREDICTED_ALLIANCE_IN_MATCH.value,
                Constants.RED, matchNumber.toString(),
                "has_actual_data").toBoolean())) Constants.FIELDS_TO_BE_DISPLAYED_MATCH_DETAILS_PLAYED else Constants.FIELDS_TO_BE_DISPLAYED_MATCH_DETAILS_NOT_PLAYED)

            root.lv_match_details.adapter =
                MatchDetailsAdapter(
                    context = activity!!,
                    datapointsDisplay = datapointsDisplay,
                    teamNumber = getTeamNumbersList(root)
                )
//        }
    }

    // Prepare the MatchDetails activity by populating each text view and other XML element
    // with its match specific information.
    private fun populateMatchDetailsEssentials(root: View) {
        // If a fragment intent (bundle arguments) exists from the previous activity (MainViewerActivity),
        // then set the match number display on MatchDetails to the match number provided with the intent.

        // If the match number from the MainViewerActivity's match schedule list view cell position
        // is null, the default display will show '0' for the match number on MatchDetails.
        root.tv_match_number_display.
            text = matchNumber.toString()

        for (tv in getHeaderCollection(root)) {
            if (getHeaderCollection(root).indexOf(tv) < 3) {
                val newText = getAllianceInMatchObjectByKey(
                    Constants.PROCESSED_OBJECT.CALCULATED_PREDICTED_ALLIANCE_IN_MATCH.value,
                    Constants.BLUE, matchNumber.toString(),
                    headerDisplay[getHeaderCollection(root).indexOf(tv)])
                if (newText == Constants.NULL_CHARACTER) {tv.text = Constants.NULL_CHARACTER}
                else {tv.text = parseFloat(("%.2f").format(newText.toFloat())).toString()}
            } else {
                val newText = getAllianceInMatchObjectByKey(
                    Constants.PROCESSED_OBJECT.CALCULATED_PREDICTED_ALLIANCE_IN_MATCH.value,
                    Constants.RED, matchNumber.toString(),
                    headerDisplay[getHeaderCollection(root).indexOf(tv) - 3])
                if (newText == Constants.NULL_CHARACTER) {tv.text = Constants.NULL_CHARACTER}
                else {tv.text = parseFloat(("%.2f").format(newText.toFloat())).toString()}
            }
        }

        for (tv in getHeaderLabelCollection(root)) {
            val headerLabelIndex = getHeaderLabelCollection(root).indexOf(tv)

            when {
                (headerLabelIndex == 0) or (headerLabelIndex == 3) ->
                    tv.text = Translations.ACTUAL_TO_HUMAN_READABLE[headerDisplay[0]]
                (headerLabelIndex == 1) or (headerLabelIndex == 4) ->
                    tv.text = Translations.ACTUAL_TO_HUMAN_READABLE[headerDisplay[1]]
                (headerLabelIndex == 2) or (headerLabelIndex == 5) ->
                    tv.text = Translations.ACTUAL_TO_HUMAN_READABLE[headerDisplay[2]]
            }
        }
    }
}