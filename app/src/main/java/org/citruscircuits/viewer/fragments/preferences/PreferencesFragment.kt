package org.citruscircuits.viewer.fragments.preferences

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.fragment_preferences.*
import kotlinx.android.synthetic.main.fragment_preferences.view.*
import kotlinx.coroutines.launch
import org.citruscircuits.viewer.MainViewerActivity
import org.citruscircuits.viewer.MainViewerActivity.StarredMatches
import org.citruscircuits.viewer.MainViewerActivity.UserDatapoints
import org.citruscircuits.viewer.R
import org.citruscircuits.viewer.constants.Constants
import org.citruscircuits.viewer.data.getDataFromWebsite
import org.citruscircuits.viewer.data.loadTestData
import org.citruscircuits.viewer.fragments.user_preferences.UserPreferencesFragment
import java.util.*

// Preferences page
class PreferencesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?


    ): View? {

        val root = inflater.inflate(R.layout.fragment_preferences, container, false)
        val versionNumber = this.getString(R.string.tv_version_num, Constants.VERSION_NUM)
        root.tv_version_num.text = versionNumber
        context?.let { createSpinner(it, root.spin_user, R.array.user_array) }
        val name =
            UserDatapoints.contents?.get("selected")?.asString?.lowercase(Locale.getDefault())
                ?.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.getDefault()
                    ) else it.toString()
                }
        val namePosition = resources.getStringArray(R.array.user_array).indexOf(name)
        root.spin_user.setSelection(namePosition)

        root.btn_user_pref_edit.setOnClickListener {
            val userPreferencesFragment = UserPreferencesFragment()

            requireFragmentManager().beginTransaction().addToBackStack(null).replace(
                (requireView().parent as ViewGroup).id,
                userPreferencesFragment
            ).commit()
        }

        // If all of our matches are already starred then it sets the Star Our Matches toggle to true
        root.tb_highlight_our_matches.isChecked =
            MainViewerActivity.starredMatches.containsAll(StarredMatches.citrusMatches)

        root.et_event_key.hint = Constants.DEFAULT_KEY
        root.et_schedule_key.hint = Constants.DEFAULT_SCHEDULE
        root.et_event_key.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                Constants.EVENT_KEY = p0.toString()
                Log.d("a", Constants.EVENT_KEY)


                MainViewerActivity.refreshManager.start(lifecycleScope)

                lifecycleScope.launch { getData() }


                if (p0.toString() == ("") || p0.isNullOrEmpty()) {
                    Constants.EVENT_KEY = Constants.DEFAULT_KEY
                    MainViewerActivity.refreshManager.start(lifecycleScope)

                    lifecycleScope.launch { getData() }
                }

            }
        })

        root.et_schedule_key.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                Constants.SCHEDULE_KEY = p0.toString()
                Log.d("a", Constants.SCHEDULE_KEY)

                MainViewerActivity.refreshManager.start(lifecycleScope)

                lifecycleScope.launch { getData() }


                if (p0.toString().equals("") || p0.isNullOrEmpty()) {
                    Constants.SCHEDULE_KEY = Constants.DEFAULT_SCHEDULE
                    MainViewerActivity.refreshManager.start(lifecycleScope)

                    lifecycleScope.launch { getData() }
                }

            }
        })


        root.tb_highlight_our_matches.setOnClickListener { starOurMatches() }

        return root
    }

    private suspend fun getData() {
        try {
            if (Constants.USE_TEST_DATA) {
                loadTestData(this.resources)
            } else {
                // Tries to get data from website when starting the app and throws an error if fails
                getDataFromWebsite()
            }
        } catch (e: Throwable) {
            Log.e(
                "data",
                "Error fetching data from ${if (Constants.USE_TEST_DATA) "files" else "website"}: ${
                    Log.getStackTraceString(
                        e
                    )
                }"
            )
        }
    }

    private fun createSpinner(context: Context, spinner: Spinner, array: Int) {

        ArrayAdapter.createFromResource(
            context, array, R.layout.spinner_layout
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.spinner_layout)
            spinner.adapter = adapter
        }


        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {
                var userName: String = spinner.selectedItem.toString()
                    .uppercase(Locale.getDefault())
                UserDatapoints.contents?.remove("selected")
                UserDatapoints.contents?.addProperty("selected", userName)
                UserDatapoints.write()
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {
                return
            }
        }
    }

    // Stars all of the matches that team 1678 is in
    private fun starOurMatches() {

        if (tb_highlight_our_matches.isChecked) {
            MainViewerActivity.starredMatches += (StarredMatches.citrusMatches)
        } else {
            MainViewerActivity.starredMatches -= (StarredMatches.citrusMatches)
        }
        StarredMatches.input()
    }
}
