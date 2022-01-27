package com.example.viewer_2020.fragments.team_details

import android.content.Intent
import android.graphics.Color
import android.graphics.Color.rgb
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
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

        root.tv_team_number.text = teamNumber
        root.tv_datapoint.text = Translations.ACTUAL_TO_HUMAN_READABLE[datapoint]

        val timDatapoint = Translations.TIM_FROM_TEAM[datapoint!!]
        root.y_axis_label.text = Translations.TIM_TO_HUMAN_READABLE[datapoint!!]

        //get data
        val timDataMap : Map<String, String> = if(timDatapoint == "auto_line"){
            getTIMDataValue(teamNumber!!, timDatapoint,
                Constants.PROCESSED_OBJECT.CALCULATED_TBA_TEAM_IN_MATCH.value)
        }else{
            getTIMDataValue(teamNumber!!, timDatapoint!!,
                Constants.PROCESSED_OBJECT.CALCULATED_OBJECTIVE_TEAM_IN_MATCH.value)
        }

        var timDataMapClimbLevel : Map<String, String>? = null
        if(Constants.GRAPHABLE_CLIMB_TIMES.contains(datapoint!!)){
            timDataMapClimbLevel = getTIMDataValue(teamNumber!!, "climb_level",
                Constants.PROCESSED_OBJECT.CALCULATED_OBJECTIVE_TEAM_IN_MATCH.value)
        }

        //add data to a list of BarEntries so it can be added to the chart
        val entries: ArrayList<BarEntry> = ArrayList()
        for(timData in timDataMap){
            if((datapoint=="matches_incap") or (datapoint=="climb_all_attempts")){
                if((timData.value != "0") and (timData.value != Constants.NULL_CHARACTER)){
                    entries.add(BarEntry(timData.key.toFloat(), 1F))
                } else{
                    entries.add(BarEntry(timData.key.toFloat(), 0F))
                }
            } else if (datapoint=="low_rung_successes"){
                if(timData.value == "Low"){
                    entries.add(BarEntry(timData.key.toFloat(), 1F))
                } else{
                    entries.add(BarEntry(timData.key.toFloat(), 0F))
                }
            } else if (datapoint=="mid_rung_successes"){
                if(timData.value == "Mid"){
                    entries.add(BarEntry(timData.key.toFloat(), 1F))
                } else{
                    entries.add(BarEntry(timData.key.toFloat(), 0F))
                }
            } else if (datapoint=="high_rung_successes"){
                if(timData.value == "High"){
                    entries.add(BarEntry(timData.key.toFloat(), 1F))
                } else{
                    entries.add(BarEntry(timData.key.toFloat(), 0F))
                }
            } else if (datapoint=="traversal_rung_successes"){
                if(timData.value == "Traversal"){
                    entries.add(BarEntry(timData.key.toFloat(), 1F))
                } else{
                    entries.add(BarEntry(timData.key.toFloat(), 0F))
                }
            } else if (datapoint == "climb_all_success_avg_time"){
                if((timDataMapClimbLevel!![timData.key] != "none") and
                    (timDataMapClimbLevel[timData.key] != Constants.NULL_CHARACTER)){
                    entries.add(BarEntry(timData.key.toFloat(), timData.value.toFloat()))
                } else{
                    entries.add(BarEntry(timData.key.toFloat(), 0F))
                }
            } else if (datapoint == "low_avg_time"){
                if(timDataMapClimbLevel!![timData.key] == "Low"){
                    entries.add(BarEntry(timData.key.toFloat(), timData.value.toFloat()))
                } else{
                    entries.add(BarEntry(timData.key.toFloat(), 0F))
                }
            } else if (datapoint == "mid_avg_time"){
                if(timDataMapClimbLevel!![timData.key] == "Mid"){
                    entries.add(BarEntry(timData.key.toFloat(), timData.value.toFloat()))
                } else{
                    entries.add(BarEntry(timData.key.toFloat(), 0F))
                }
            } else if (datapoint == "high_avg_time"){
                if(timDataMapClimbLevel!![timData.key] == "High"){
                    entries.add(BarEntry(timData.key.toFloat(), timData.value.toFloat()))
                } else{
                    entries.add(BarEntry(timData.key.toFloat(), 0F))
                }
            } else if (datapoint == "traversal_avg_time"){
                if(timDataMapClimbLevel!![timData.key] == "Traversal"){
                    entries.add(BarEntry(timData.key.toFloat(), timData.value.toFloat()))
                } else{
                    entries.add(BarEntry(timData.key.toFloat(), 0F))
                }
            } else if (datapoint == "climb_percent_success"){
                if((timData.value != "none") and (timData.value != Constants.NULL_CHARACTER)){
                    entries.add(BarEntry(timData.key.toFloat(), 1F))
                } else{
                    entries.add(BarEntry(timData.key.toFloat(), 0F))
                }
            }
            else if(Constants.GRAPHABLE_BOOL.contains(datapoint!!)) {
                if (timData.value == "true") {
                    entries.add(BarEntry(timData.key.toFloat(), 1F))
                }else{
                    entries.add(BarEntry(timData.key.toFloat(), 0F))
                }
            } else if(timData.value != Constants.NULL_CHARACTER){
                entries.add(BarEntry(timData.key.toFloat(), timData.value.toFloat()))
            } else{
                entries.add(BarEntry(timData.key.toFloat(), 0F))
            }
        }

        //make the list of entries into a BarDataSet so it can be added to the chart
        val barDataSet = BarDataSet(entries, "")

        //set color of bars
        barDataSet.setColors(ContextCompat.getColor(
            context!!,
            R.color.colorPrimaryLight))

        //set text size of the numbers labelling the height of each bar
        barDataSet.valueTextSize = 18F

        //set labels to only be integers
        root.bar_chart.xAxis.granularity = 1.0f
        root.bar_chart.xAxis.isGranularityEnabled = true
        if (Constants.GRAPHABLE_BOOL.contains(datapoint!!) or Constants.GRAPHABLE.contains(datapoint!!)
        and (datapoint != "avg_incap_time")){
            root.bar_chart.axisLeft.granularity = 1.0f
            root.bar_chart.axisLeft.isGranularityEnabled = true
            root.bar_chart.axisRight.granularity = 1.0f
            root.bar_chart.axisRight.isGranularityEnabled = true
        }

        //add extra margins around the chart to accommodate increased text size of labels
        root.bar_chart.extraBottomOffset = 15F
        root.bar_chart.extraLeftOffset = 10F
        root.bar_chart.extraRightOffset = 15F

        //increase text size of labels (the numbers on the axes)
        root.bar_chart.xAxis.textSize = 18F
        root.bar_chart.axisLeft.textSize = 18F
        root.bar_chart.axisRight.textSize = 18F

        //put xAxis on the bottom instead of the top
        root.bar_chart.xAxis.position = XAxis.XAxisPosition.BOTTOM

        //set yAxis minimum to 0
        root.bar_chart.axisLeft.axisMinimum = 0F
        root.bar_chart.axisRight.axisMinimum = 0F

        //chart the data
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

        //set up chart to detect clicking on different bars
        root.bar_chart.isHighlightFullBarEnabled = true

        //change the sensitivity of how close a click must be to a bar to register
        root.bar_chart.maxHighlightDistance = 11F

        //define function for what to do after a bar is clicked
        fun getOnChartValueSelectedListener(): OnChartValueSelectedListener {
            return object : OnChartValueSelectedListener {
                override fun onValueSelected(e: Entry?, h: Highlight?) {
                    root.bar_chart.setTouchEnabled(false)
                    //set match number to the x value of the entry selected
                    val matchNumberClicked: Int = e!!.x.toInt()
                    Log.e("match number", "$matchNumberClicked")
                    teamDetailsFragmentArguments.putString(Constants.TEAM_NUMBER, teamNumber)
                    //TODO: Change to opening the TIM fragment once available
                    //teamDetailsFragmentArguments.putString(Constants.MATCH_NUMBER, matchNumberClicked)
                    teamDetailsFragment.arguments = teamDetailsFragmentArguments
                    fragmentManager!!.beginTransaction().addToBackStack(null).replace(
                        (view!!.parent as ViewGroup).id,
                        teamDetailsFragment
                    ).commit()
                }

                override fun onNothingSelected() {
               }
            }
        }

        //set on click listener to the function created
        root.bar_chart.setOnChartValueSelectedListener(getOnChartValueSelectedListener())

        return root
    }
}