package com.example.viewer_2022.fragments.team_details

import android.graphics.Canvas
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import co.yml.charts.axis.AxisData
import co.yml.charts.common.model.Point
import co.yml.charts.ui.barchart.BarChart
import co.yml.charts.ui.barchart.models.BarChartData
import co.yml.charts.ui.barchart.models.BarData
import com.example.viewer_2022.R
import com.example.viewer_2022.constants.Constants
import com.example.viewer_2022.constants.Translations
import com.example.viewer_2022.databinding.FragmentGraphsBinding
import com.example.viewer_2022.getTIMDataValue

/**
 * Page that the graphs are displayed on
 */
class GraphsFragment : Fragment() {


    private var _binding: FragmentGraphsBinding? = null


    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGraphsBinding.inflate(inflater, container, false)
        val view = binding.root

        val teamNumber =
            requireArguments().getString(Constants.TEAM_NUMBER, Constants.NULL_CHARACTER)
        val datapoint = requireArguments().getString("datapoint", Constants.NULL_CHARACTER)

        val timDataMap = getTIMDataValue(teamNumber, Translations.TIM_FROM_TEAM[datapoint]!!)
        val barData = buildList<BarData> {
            timDataMap.toList().forEachIndexed { i, (matchNumber, value) ->

                add(BarData(Point(i.toFloat(), value?.toFloatOrNull() ?: 0F), label = matchNumber))
            }
        }
        val maxRange = 50
        val yStepSize = 10

        val xAxisData = AxisData.Builder()
            .axisStepSize(30.dp)
            .steps(barData.size - 1)
            .bottomPadding(40.dp)
            .axisLabelAngle(20f)
            .labelData { index -> barData[index].label }
            .build()
        val yAxisData = AxisData.Builder()
            .steps(yStepSize)
            .labelAndAxisLinePadding(20.dp)
            .axisOffset(20.dp)
            .labelData { index -> (index * (maxRange / yStepSize)).toString() }
            .build()

        binding.composeView.setContent {
            BarChart(Modifier, BarChartData(barData, xAxisData, yAxisData))
        }
        return view
    }
}