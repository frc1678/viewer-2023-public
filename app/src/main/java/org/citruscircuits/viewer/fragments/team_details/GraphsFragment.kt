package org.citruscircuits.viewer.fragments.team_details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import co.yml.charts.axis.AxisData
import co.yml.charts.common.model.Point
import co.yml.charts.ui.barchart.BarChart
import co.yml.charts.ui.barchart.models.BarChartData
import co.yml.charts.ui.barchart.models.BarData
import co.yml.charts.ui.barchart.models.BarStyle
import co.yml.charts.ui.barchart.models.SelectionHighlightData
import org.citruscircuits.viewer.constants.Constants
import org.citruscircuits.viewer.constants.Translations
import org.citruscircuits.viewer.databinding.FragmentGraphsBinding
import org.citruscircuits.viewer.getTIMDataValue
import kotlin.math.ceil
import kotlin.math.roundToInt

/**
 * [Fragment] for displaying a graph of a TIM data point for a given team.
 */
class GraphsFragment : Fragment() {

    private var _binding: FragmentGraphsBinding? = null

    /**
     * This property is only valid between [onCreateView] and [onDestroyView].
     */
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Creating a binding that allows type safe access to ComposeView
        _binding = FragmentGraphsBinding.inflate(inflater, container, false)
        // Get the team number from the fragment arguments
        val teamNumber =
            requireArguments().getString(Constants.TEAM_NUMBER, Constants.NULL_CHARACTER)
        // Get the data point name from the fragment arguments
        val dataPoint = requireArguments().getString("datapoint", Constants.NULL_CHARACTER)
        // Get whether charge levels should be shown instead of numbers
        val showingChargeLevels =
            dataPoint == "auto_charge_level" || dataPoint == "tele_charge_level"
        val showingTippyOrDefense =
            dataPoint == "was_tippy" || dataPoint == "played_defense"
        // Get the TIM data for the data point of the team
        val timDataMap = getTIMDataValue(teamNumber, dataPoint)
        // Set the data to be displayed by each bar
        val barData = timDataMap.toList().mapIndexed { i, (_, value) ->
            BarData(
                Point(
                    // match number index
                    x = i.toFloat(),
                    // value
                    y = if (showingChargeLevels) Constants.CHARGE_LEVELS.indexOf(value)
                        .takeIf { it != -1 }?.toFloat() ?: 0f
                    else if (showingTippyOrDefense) {
                        (if (value == "true") 1f else 0f)
                    }
                    else value?.toFloatOrNull() ?: 0f
                ), label = "${i + 1}"
            )
        }
        // Sort the data map by highest data value and get the highest value as a float
        val maxValue = timDataMap.toList().sortedBy { it.second?.toFloatOrNull() }
            .reversed()[0].second?.toFloatOrNull()
        // Set the maximum y range by rounding up the max value to the next multiple of 5
        // Default to 50
        val maxRange = if (showingChargeLevels) Constants.CHARGE_LEVELS.lastIndex.toFloat()
            else if (showingTippyOrDefense) 1f
            else if (maxValue != null) ceil(maxValue / 5) * 5 else 50f
        val yStepSize = if (showingChargeLevels) Constants.CHARGE_LEVELS.lastIndex else if (showingTippyOrDefense) 1 else 5
        // Prepare the x and y axes with the bar data
        val xAxisData =
            AxisData.Builder().axisStepSize(30.dp).steps(barData.size - 1).bottomPadding(40.dp)
                .axisLabelAngle(0f).labelData { index -> barData[index].label }.build()
        val yAxisData =
            AxisData.Builder().steps(yStepSize).labelAndAxisLinePadding(20.dp).axisOffset(20.dp)
                .topPadding(40.dp).labelData { index ->
                    if (showingChargeLevels) Constants.CHARGE_LEVELS.getOrNull(index).toString()
                    else if (showingTippyOrDefense) (if (index == 0) "FALSE" else "TRUE")
                    else (index * (maxRange / yStepSize)).toInt().toString()
                }.build()
        // Create the label to be shown when a bar is selected
        val selectionHighlightData = SelectionHighlightData(popUpLabel = { x, y ->
            "QM${timDataMap.toList()[x.toInt()].first}: ${
                if (showingChargeLevels) Constants.CHARGE_LEVELS.getOrNull(y.roundToInt()) 
                else if (showingTippyOrDefense) (if (y == 0f) "FALSE" else "TRUE")
                else y
            }"
        })
        // Set the page content to the graph
        binding.composeView.setContent {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // The name of the data point
                Text(
                    Translations.ACTUAL_TO_HUMAN_READABLE.getOrDefault(dataPoint, dataPoint),
                    modifier = Modifier.padding(10.dp),
                    style = TextStyle(fontSize = 24.sp)
                )
                // The team number
                Text(
                    teamNumber,
                    modifier = Modifier.padding(bottom = 6.dp),
                    style = TextStyle(fontSize = 20.sp, color = Color.Gray)
                )
                // The bar chart
                BarChart(
                    modifier = Modifier.fillMaxHeight(0.9f), BarChartData(
                        barData, xAxisData, yAxisData, barStyle = BarStyle(
                            selectionHighlightData = selectionHighlightData
                        )
                    )
                )
                // The y axis label
                Text(
                    "Match Number",
                    modifier = Modifier
                        .padding(10.dp)
                        .weight(1f),
                    style = TextStyle(fontSize = 24.sp)
                )
            }
        }
        return binding.root
    }
}
