package com.example.viewer_2020

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import com.example.viewer_2020.constants.MatchDetailsConstants
import kotlinx.android.synthetic.main.fragment_preferences.*
import kotlinx.android.synthetic.main.fragment_preferences.view.*


class PreferencesFragment: IFrag() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_preferences, container, false)
        context?.let { createSpinner(it, root.spin_user, R.array.user_array) }
        var namePosition = MatchDetailsConstants.USERS.valueOf(context?.getSharedPreferences("VIEWER", 0)?.getString("username", "").toString()).ordinal
        root.spin_user.setSelection(namePosition)
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

                context.getSharedPreferences("VIEWER", 0).edit().putString("username", userName).apply()

            }

            override fun onNothingSelected(parentView: AdapterView<*>) {
                return
            }
        }

        btn_user_pref_edit.setOnClickListener {
            val userPreferencesFragment = UserPreferencesFragment()

            fragmentManager!!.beginTransaction().addToBackStack(null).replace(
                (view!!.parent as ViewGroup).id,
                userPreferencesFragment
            ).commit()
        }

    }
}
