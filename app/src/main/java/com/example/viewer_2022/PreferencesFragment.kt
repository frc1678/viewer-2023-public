package com.example.viewer_2022

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
import kotlinx.android.synthetic.main.fragment_preferences.*
import kotlinx.android.synthetic.main.fragment_preferences.view.*
import com.example.viewer_2022.MainViewerActivity.UserDatapoints
import com.example.viewer_2022.R
import com.example.viewer_2022.UserPreferencesFragment
import com.example.viewer_2022.constants.Constants

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

        val name = UserDatapoints.contents?.get("selected")?.asString?.toLowerCase()?.capitalize()
        val namePosition = resources.getStringArray(R.array.user_array).indexOf(name)
        root.spin_user.setSelection(namePosition)

        root.btn_user_pref_edit.setOnClickListener() {
            val userPreferencesFragment = UserPreferencesFragment()

            fragmentManager!!.beginTransaction().addToBackStack(null).replace(
                (view!!.parent as ViewGroup).id,
                userPreferencesFragment
            ).commit()
        }

        root.tb_highlight_our_matches.setOnClickListener { starOurMatches() }

        return root
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
                var userName: String = spin_user.selectedItem.toString().toUpperCase()

                UserDatapoints.contents?.remove("selected")
                UserDatapoints.contents?.addProperty("selected", userName)
                UserDatapoints.write()
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {
                return
            }
        }
    }

    private fun starOurMatches() {
        val citrusMatches = MainViewerActivity.matchCache.filter {
            return@filter it.value.blueTeams.contains("1678") or it.value.redTeams.contains("1678")
        }.map { return@map it.value.matchNumber }

        if (tb_highlight_our_matches.isChecked) {
            MainViewerActivity.starredMatches += (citrusMatches)
        } else {
            MainViewerActivity.starredMatches -= (citrusMatches)
        }
    }
}
