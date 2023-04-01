package org.citruscircuits.viewer.fragments.team_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.citruscircuits.viewer.constants.Constants

val testAutoPath = Json.decodeFromString<AutoPath>(
    """
     {
  "path_number": 1,
  "start_position": "1",
  "team_number": "3482",
  "auto_charge_level": "D",
  "auto_charge_successes": 1,
  "intake_1_piece": "U",
  "intake_1_position": "one",
  "intake_2_piece": "O",
  "intake_2_position": "three",
  "match_numbers": [
    1
  ],
  "matches_ran": 1,
  "mobility": false,
  "preloaded_gamepiece": "U",
  "score_1_max_piece_successes": 0,
  "score_1_piece": "fail",
  "score_1_piece_successes": 0,
  "score_1_position": "fail",
  "score_2_max_piece_successes": 0,
  "score_2_piece": null,
  "score_2_piece_successes": 0,
  "score_2_position": null,
  "score_3_max_piece_successes": 0,
  "score_3_piece": null,
  "score_3_piece_successes": 0,
  "score_3_position": null
}
""".trimIndent()
)

/**
 * Fragment for showing the auto paths of a given team.
 */
class AutoPathsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ) = ComposeView(requireContext()).apply {
        setContent {
            Column(modifier = Modifier.padding(horizontal = 10.dp)) {
                val pageCount = 10
                var currentPage by remember { mutableStateOf(0) }
                Text(
                    "Auto Paths for ${requireArguments().getString(Constants.TEAM_NUMBER)}",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(vertical = 10.dp)
                )
                Text("Ran ${testAutoPath.matches_ran} time(s)")
                Text(
                    "Preloaded: ${
                        when (testAutoPath.preloaded_gamepiece) {
                            "O" -> "Cone"
                            "U" -> "Cube"
                            else -> "None"
                        }
                    }"
                )
                AutoPath(testAutoPath)
                Row(
                    Modifier
                        .height(50.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { currentPage-- },
                        enabled = currentPage > 0,
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowLeft, contentDescription = null
                        )
                    }
                    LazyRow {
                        repeat(pageCount) { iteration ->
                            item {
                                Box(modifier = Modifier
                                    .clickable { currentPage = iteration }
                                    .padding(2.dp)
                                    .clip(CircleShape)
                                    .background(if (currentPage == iteration) Color.DarkGray else Color.LightGray)
                                    .size(20.dp))
                            }
                        }
                    }
                    IconButton(
                        onClick = { currentPage++ },
                        enabled = currentPage < pageCount - 1,
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowRight,
                            contentDescription = null
                        )
                    }
                }
            }
        }
    }
}
