package com.example.viewer_2020.fragments.team_details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.viewer_2020.R
import com.example.viewer_2020.constants.Constants
import com.example.viewer_2020.constants.Translations
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
        Log.e("here", "in graphs fragment")
        val root = inflater.inflate(R.layout.fragment_graphs, container, false)

        arguments?.let {
            teamNumber = it.getString(Constants.TEAM_NUMBER, Constants.NULL_CHARACTER)
            datapoint = it.getString("datapoint", Constants.NULL_CHARACTER)
        }
        Log.e("here", "$teamNumber, $datapoint")

        val timDatapoint = Translations.AVG_TO_TIM[datapoint]

        val entries: ArrayList<BarEntry> = ArrayList()
        entries.add(BarEntry(1f, 4f))
        entries.add(BarEntry(2f, 10f))
        entries.add(BarEntry(3f, 2f))
        entries.add(BarEntry(4f, 15f))
        entries.add(BarEntry(5f, 13f))
        entries.add(BarEntry(6f, 2f))

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