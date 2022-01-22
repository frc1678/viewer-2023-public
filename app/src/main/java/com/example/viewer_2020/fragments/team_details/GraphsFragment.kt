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
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.android.synthetic.main.fragment_graphs.view.*


class GraphsFragment : Fragment() {
    private var teamNumber: String? = null
    private var datapoint: String? = null
    private val teamDetailsFragmentArguments = Bundle()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_graphs, container, false)
        val teamDetailsFragment = TeamDetailsFragment()

        arguments?.let {
            teamNumber = it.getString(Constants.TEAM_NUMBER, Constants.NULL_CHARACTER)
            datapoint = it.getString("datapoint", Constants.NULL_CHARACTER)
        }

        val timDatapoint = Translations.TIM_FROM_TEAM[datapoint!!]
        root.y_axis_label.text = Translations.TIM_TO_HUMAN_READABLE[timDatapoint]

        val timDataMap = getTIMDataValue(teamNumber!!, timDatapoint!!, Constants.PROCESSED_OBJECT.CALCULATED_OBJECTIVE_TEAM_IN_MATCH.value)
        Log.e("important", "$timDataMap")

        val entries: ArrayList<BarEntry> = ArrayList()
        for(timData in timDataMap){
            entries.add(BarEntry(timData.key.toFloat(), timData.value.toFloat()))
        }
        Log.e("important", "here: $entries")

        val barDataSet = BarDataSet(entries, "")
        barDataSet.setColors(*ColorTemplate.COLORFUL_COLORS)
        barDataSet.valueTextSize = 18F
        root.bar_chart.extraBottomOffset = 15F
        root.bar_chart.extraLeftOffset = 15F
        root.bar_chart.extraRightOffset = 15F
        root.bar_chart.xAxis.textSize = 18F
        root.bar_chart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        root.bar_chart.axisLeft.textSize = 18F
        root.bar_chart.axisRight.textSize = 18F
        root.bar_chart.axisLeft.axisMinimum = 0F
        root.bar_chart.axisRight.axisMinimum = 0F

        val data = BarData(barDataSet)
        root.bar_chart.data = data

        //show grid lines
        root.bar_chart.axisLeft.setDrawGridLines(true)
        root.bar_chart.xAxis.setDrawGridLines(true)
        root.bar_chart.xAxis.setDrawAxisLine(true)

        //right y-axis
        root.bar_chart.axisRight.isEnabled = true

        //remove legend
        root.bar_chart.legend.isEnabled = false

        //remove description label
        root.bar_chart.description.isEnabled = false

        //disable zooming
        root.bar_chart.isDoubleTapToZoomEnabled = false
        root.bar_chart.setPinchZoom(false)
        root.bar_chart.setScaleEnabled(false)

        //draw chart
        root.bar_chart.invalidate()

        root.bar_chart.setOnClickListener(){
            //Change to open TIMD fragment when we have it
            teamDetailsFragmentArguments.putString(Constants.TEAM_NUMBER, teamNumber)
            teamDetailsFragment.arguments = teamDetailsFragmentArguments
            this.fragmentManager!!.beginTransaction().addToBackStack(null).replace(
                (view!!.parent as ViewGroup).id,
                teamDetailsFragment
            ).commit()
        }

        return root
    }
}