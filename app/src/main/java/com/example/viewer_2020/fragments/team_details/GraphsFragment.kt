package com.example.viewer_2020.fragments.team_details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.viewer_2020.*
import com.example.viewer_2020.constants.Constants
import com.example.viewer_2020.constants.Translations
import com.example.viewer_2020.data.DatabaseReference
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.android.synthetic.main.fragment_graphs.*
import kotlinx.android.synthetic.main.fragment_graphs.view.*
import kotlinx.android.synthetic.main.team_details_cell.view.*


class GraphsFragment : Fragment() {
    private var teamNumber: String? = null
    private var datapoint: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Log.e("here", "in graphs fragment")
        val root = inflater.inflate(R.layout.fragment_graphs, container, false)

        arguments?.let {
            teamNumber = it.getString(Constants.TEAM_NUMBER, Constants.NULL_CHARACTER)
            datapoint = it.getString("datapoint", Constants.NULL_CHARACTER)
        }
        //Log.e("here", "$teamNumber, $datapoint")

        val timDatapoint = Translations.AVG_TO_TIM[datapoint!!]

        val things = getTIMDataValue(teamNumber!!, timDatapoint!!, Constants.PROCESSED_OBJECT.CALCULATED_OBJECTIVE_TEAM_IN_MATCH.value)
        Log.e("important", "$things")

        val test = getTeamDataValue(teamNumber!!, timDatapoint!!)
        val test2 = (getAllianceInMatchObjectByKey(
            Constants.PROCESSED_OBJECT.CALCULATED_OBJECTIVE_TEAM_IN_MATCH.value,
            Constants.BLUE, "2",
            "auto_balls_low"))
        //Log.e("important", "test2: $test2")
        val matchSchedule = getMatchSchedule(teamNumber)
        //Log.e("important", "keys ${matchSchedule.keys}")
        //Log.e("important", "values ${matchSchedule.values}")
        //Log.e("here", "match schedule: $matchSchedule")

        //Log.e("here", test)
        //Log.e("here", "alliance : $test2")

        val entries: ArrayList<BarEntry> = ArrayList()
        for(thing in things){
            entries.add(BarEntry(thing.key.toFloat(), thing.value.toFloat()))
        }
        Log.e("important", "here: $entries")

        val barDataSet = BarDataSet(entries, "")
        barDataSet.setColors(*ColorTemplate.COLORFUL_COLORS)

        val data = BarData(barDataSet)
        root.bar_chart.data = data

        //hide grid lines
        root.bar_chart.axisLeft.setDrawGridLines(true)
        root.bar_chart.xAxis.setDrawGridLines(true)
        root.bar_chart.xAxis.setDrawAxisLine(true)

        //remove right y-axis
        root.bar_chart.axisRight.isEnabled = true

        //remove legend
        root.bar_chart.legend.isEnabled = false

        //remove description label
        root.bar_chart.description.isEnabled = false

        //draw chart
        root.bar_chart.invalidate()

        return root
    }
}