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
import org.citruscircuits.viewer.StartupActivity
import org.citruscircuits.viewer.constants.Constants
import org.citruscircuits.viewer.data.AutoPath

/**
 * Fragment for showing the auto paths of a given team.
 */
class AutoPathsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ) = ComposeView(requireContext()).apply {
        val teamNumber = requireArguments().getString(Constants.TEAM_NUMBER)!!
        val autoPaths = mutableListOf<Pair<String, AutoPath>>()
        StartupActivity.databaseReference!!.auto_paths[teamNumber]?.forEach { (startingPosition, paths) ->
            paths.forEach { (_, path) -> autoPaths += startingPosition to path }
        }
        setContent {
            if (autoPaths.isNotEmpty()) Column(modifier = Modifier.padding(horizontal = 10.dp)) {
                var currentPage by remember { mutableStateOf(0) }
                Text(
                    "Auto Paths for $teamNumber",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(vertical = 10.dp)
                )
                Text("Ran ${autoPaths[currentPage].second.matches_ran} time(s)")
                Text(
                    "Preloaded: ${
                        when (autoPaths[currentPage].second.preloaded_gamepiece) {
                            "O" -> "Cone"
                            "U" -> "Cube"
                            else -> "None"
                        }
                    }"
                )
                AutoPath(autoPaths[currentPage].first, autoPaths[currentPage].second)
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
                        repeat(autoPaths.size) { iteration ->
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
                        enabled = currentPage < autoPaths.size - 1,
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
