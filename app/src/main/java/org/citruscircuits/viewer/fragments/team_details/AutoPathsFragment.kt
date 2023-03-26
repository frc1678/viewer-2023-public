package org.citruscircuits.viewer.fragments.team_details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.material3.Text
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import org.citruscircuits.viewer.constants.Constants

/**
 * Fragment for showing the auto paths of a given team.
 */
class AutoPathsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ) = ComposeView(requireContext()).apply {
        Log.d("AutoPathsFragment", "arguments: $arguments")
        setContent {
            Text("Auto Paths for team ${requireArguments().getString(Constants.TEAM_NUMBER)}")
        }
    }
}
