package com.example.viewer_2020

import android.content.Context
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.example.viewer_2020.constants.Translations
import kotlinx.android.synthetic.main.team_details_cell.view.*
import java.util.regex.Pattern

class UserPreferencesAdapter(
    private val context: FragmentActivity,
    private val datapointsDisplayed: List<String>
) : BaseAdapter() {

    private val inflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return datapointsDisplayed.size
    }

    // Returns the specific data point given the position of the data point.
    override fun getItem(position: Int): String {
        return datapointsDisplayed[position]
    }

    // Returns the position of the cell.
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val e = getItem(position)
        val rowView = inflater.inflate(R.layout.user_pref_cell, parent, false)

        rowView.tv_datapoint_name.text =
            Translations.ACTUAL_TO_HUMAN_READABLE[e]
                ?: e
        if ((e == "Auto") or (e == "Tele") or (e == "Endgame") or (e == "Pit Data")) {
            rowView.tv_datapoint_name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 28F)
            rowView.tv_datapoint_name.gravity = Gravity.CENTER_HORIZONTAL
            val noWidth = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 0f)
            val allWidth = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1f)
            rowView.tv_ranking.layoutParams = noWidth
            rowView.tv_datapoint_name.layoutParams = allWidth
            rowView.tv_datapoint_value.layoutParams = noWidth
            rowView.tv_datapoint_name.setBackgroundColor(
                ContextCompat.getColor(
                    context,
                    R.color.LightGray
                )
            )
            rowView.tv_datapoint_name.setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.Black
                )
            )
        }
        return rowView
    }
}