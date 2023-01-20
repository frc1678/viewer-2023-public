package com.example.viewer_2022.fragments.team_details

import android.os.Bundle
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
import com.example.viewer_2022.constants.Constants
import com.example.viewer_2022.constants.Translations
import com.example.viewer_2022.databinding.FragmentGraphsBinding
import com.example.viewer_2022.getTIMDataValue
import kotlin.math.ceil

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
        //Creating a binding that allows type safe access to ComposeView
        _binding = FragmentGraphsBinding.inflate(inflater, container, false)
        val view = binding.root
        //Assign teamNumber + datapoint to the correct value from arguments of the fragment
        val teamNumber =
            requireArguments().getString(Constants.TEAM_NUMBER, Constants.NULL_CHARACTER)
        val datapoint = requireArguments().getString("datapoint", Constants.NULL_CHARACTER)
        //Getting the timDataMap for the datapoint of the team.
        val timDataMap = getTIMDataValue(teamNumber, datapoint)
        //Creating barData by looping through each index of timDataMap and adding a BarData of x value the index of the matchNumber
        //y value is the value
        val barData = buildList<BarData> {
            timDataMap.toList().forEachIndexed { i, (matchNumber, value) ->

                add(BarData(Point(i.toFloat(), value?.toFloatOrNull() ?: 0F), label = matchNumber))
            }
        }

        //Sorting the data map by highest data value and returning the highest value as a float

        val maxValue = timDataMap.toList().sortedBy { it.second?.toFloatOrNull() }
            .reversed()[0].second?.toFloatOrNull()
        //Setting the maximum y range by rounding up the max value to the next highest multiple of 5 and on default be 50 as a float
        val maxRange = if (maxValue != null) (ceil(maxValue / 5) * 5) else 50f

        val yStepSize = 5
        //Preparing the x and y axis with the barData with the barData we created earlier
        val xAxisData = AxisData.Builder()
            .axisStepSize(30.dp)
            .steps(barData.size - 1)
            .bottomPadding(40.dp)
            .axisLabelAngle(0f)
            .labelData { index -> barData[index].label }
            .build()
        val yAxisData = AxisData.Builder()
            .steps(yStepSize)
            .labelAndAxisLinePadding(20.dp)
            .axisOffset(20.dp)
            .labelData { index -> (index * (maxRange / yStepSize)).toString() }
            .build()

        //Creating content of compose view
        binding.composeView.setContent {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    Translations.ACTUAL_TO_HUMAN_READABLE.getOrDefault(datapoint, datapoint),
                    modifier = Modifier.padding(10.dp), style = TextStyle(fontSize = 24.sp)
                )
                Text(
                    teamNumber.toString(),
                    modifier = Modifier.padding(bottom = 6.dp),
                    style = TextStyle(fontSize = 20.sp, color = Color.Gray)
                )
                //Creates actual chart with barData and axis and adds extra highlight text to qualification match
                BarChart(
                    Modifier.fillMaxHeight(0.9F),
                    BarChartData(
                        barData, xAxisData, yAxisData,
                        barStyle = BarStyle(
                            selectionHighlightData = SelectionHighlightData(
                                popUpLabel = { x, y -> "QM${timDataMap.toList()[x.toInt()].first}: $y" })
                        )
                    )
                )

                Text(
                    "Match Number",
                    Modifier
                        .padding(10.dp)
                        .weight(1F), style = TextStyle(fontSize = 24.sp)
                )
            }

        }
        return view
    }
}