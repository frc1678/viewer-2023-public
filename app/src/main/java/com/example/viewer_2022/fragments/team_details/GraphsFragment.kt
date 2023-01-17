package com.example.viewer_2022.fragments.team_details
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.border
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
            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceBetween) {

                Text(Translations.ACTUAL_TO_HUMAN_READABLE.getOrDefault(datapoint, datapoint),
                    modifier  = Modifier.padding(10.dp), style = TextStyle(fontSize = 24.sp)
                )
                Text(teamNumber.toString(), modifier = Modifier.padding(bottom = 6.dp), style = TextStyle(fontSize = 20.sp, color = Color.Gray))
                BarChart(Modifier.fillMaxHeight(0.9F), BarChartData(barData, xAxisData, yAxisData))

                Text("Match Number", Modifier.padding(10.dp).weight(1F), style = TextStyle(fontSize = 24.sp))
            }

        }
        return view
    }
}