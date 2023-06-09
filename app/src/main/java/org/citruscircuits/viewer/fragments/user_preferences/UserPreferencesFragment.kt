package org.citruscircuits.viewer.fragments.user_preferences

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.citruscircuits.viewer.MainViewerActivity.UserDatapoints
import org.citruscircuits.viewer.R
import org.citruscircuits.viewer.constants.Constants
import com.google.gson.JsonArray
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.fragment_user_pref.view.*
import java.io.InputStreamReader
import java.util.*

/**
 * Page to select the user's preference of datapoints to be displayed.
 */
class UserPreferencesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_user_pref, container, false)

        val userName = UserDatapoints.contents?.get("selected")?.asString
        root.user_datapoints_header.text = if (userName == "OTHER") {
            "User's Datapoints"
        } else {
            "${
                userName?.lowercase(Locale.getDefault())
                    ?.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
            }'s Datapoints"
        }

        updateUserDatapointsListView(root)

        root.reset_button.setOnClickListener {
            val defaultsJsonArray: JsonArray = JsonParser.parseReader(
                InputStreamReader
                    (context?.resources?.openRawResource(R.raw.default_prefs))
            )
                .asJsonObject.get(userName).asJsonArray

            UserDatapoints.contents?.remove(userName)
            UserDatapoints.contents?.add(userName, defaultsJsonArray)
            UserDatapoints.write()

            root.lv_user_datapoints.invalidateViews()
            updateUserDatapointsListView(root)
        }

        root.select_all_button.setOnClickListener {
            val newJsonArray = JsonArray()
            for (datapoint in Constants.FIELDS_TO_BE_DISPLAYED_TEAM_DETAILS) {
                newJsonArray.add(datapoint)
            }
            UserDatapoints.contents?.remove(userName)
            UserDatapoints.contents?.add(userName, newJsonArray)
            UserDatapoints.write()

            root.lv_user_datapoints.invalidateViews()
            updateUserDatapointsListView(root)
        }
        return root
    }

    private fun updateUserDatapointsListView(root: View) {
        val datapointsDisplay = Constants.FIELDS_TO_BE_DISPLAYED_TEAM_DETAILS

        val adapter = UserPreferencesAdapter(
            context = requireActivity(),
            datapointsDisplayed = datapointsDisplay - listOf("See Matches", "Notes Label", "Notes")

        )
        root.lv_user_datapoints.adapter = adapter

    }
}
