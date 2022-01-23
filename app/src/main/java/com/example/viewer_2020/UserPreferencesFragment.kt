package com.example.viewer_2020

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.viewer_2020.constants.Constants
import com.example.viewer_2020.fragments.team_details.TeamDetailsAdapter
import kotlinx.android.synthetic.main.fragment_user_pref.view.*
import kotlinx.android.synthetic.main.team_details.view.*
import kotlinx.android.synthetic.main.team_details.view.lv_datapoint_display

class UserPreferencesFragment: IFrag() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_user_pref, container, false)

        updateUserDatapointsListView(root)

        return root
    }

    private fun updateUserDatapointsListView(root: View) {
        adapter = UserPreferencesAdapter(
            context = activity!!,
            datapointsDisplayed = Constants.FIELDS_TO_BE_DISPLAYED_TEAM_DETAILS
        )
        root.lv_user_datapoints.adapter = adapter

    }
}