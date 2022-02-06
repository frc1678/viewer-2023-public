package com.example.viewer_2020

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.viewer_2020.MainViewerActivity.UserDatapoints
import com.example.viewer_2020.constants.Constants
import com.google.gson.JsonArray
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.fragment_user_pref.view.*
import java.io.InputStreamReader

class UserPreferencesFragment: IFrag() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_user_pref, container, false)

        val userName = UserDatapoints.contents?.get("selected")?.asString

        root.user_datapoints_header.text = if (userName == "NONE"){
            "User's Datapoints"
        } else {
            "${userName?.toLowerCase()?.capitalize()}'s Datapoints"
        }

        updateUserDatapointsListView(root)

        root.reset_button.setOnClickListener(){
            val defaultsJsonArray : JsonArray = JsonParser.parseReader(InputStreamReader
                (context?.resources?.openRawResource(R.raw.default_prefs)))
                .asJsonObject.get(userName).asJsonArray

            UserDatapoints.contents?.remove(userName)
            UserDatapoints.contents?.add(userName, defaultsJsonArray)
            UserDatapoints.write()

            root.lv_user_datapoints.invalidateViews()
            updateUserDatapointsListView(root)
        }

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