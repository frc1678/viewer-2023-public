package com.example.viewer_2020.fragments.live_picklist

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.viewer_2020.IFrag
import com.example.viewer_2020.R
import com.example.viewer_2020.StartupActivity
import com.example.viewer_2020.constants.Constants
import com.example.viewer_2020.data.DatabaseReference
import com.example.viewer_2020.fragments.team_details.TeamDetailsFragment
import kotlinx.android.synthetic.main.fragment_live_picklist.view.*
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

/**
 * The fragment for the live team orderings from the picklist.
 *
 * @see R.layout.fragment_live_picklist
 */
class LivePicklistFragment : IFrag() {
    private val teamDetailsFragment = TeamDetailsFragment()

    private lateinit var root: View

    /** The list of teams with their picklist rankings, sorted by their first rank. */
    private lateinit var firstOrder: List<DatabaseReference.PicklistTeam>

    /** The list of teams with their picklist rankings, sorted by their second rank. */
    private lateinit var secondOrder: List<DatabaseReference.PicklistTeam>

    /** The possible orderings of the picklist. */
    enum class Orders { FIRST, SECOND }

    /**
     * The ordering that is currently being used. Has a custom setter method to automatically update
     * the list adapter.
     */
    private var currentOrdering = Orders.FIRST
        set(value) {
            field = value
            updateHeaders()
            updateList()
        }

    /** Runs when the fragment is created, to inflate the layout. */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the picklist layout.
        root = inflater.inflate(R.layout.fragment_live_picklist, container, false)

        // Refresh and update the list to show the data.
        refreshList()
        updateList()
        updateHeaders()

        // Register the listeners for the sorting buttons.
        root.btn_first_rank.setOnClickListener { currentOrdering = Orders.FIRST }
        root.btn_second_rank.setOnClickListener { currentOrdering = Orders.SECOND }

        // Register the listener to open the team details fragment when the user taps on a team in
        // the picklist.
        root.lv_live_picklist.setOnItemClickListener { _, _, position, _ ->
            // Put the arguments for the team details fragment.
            teamDetailsFragment.arguments = Bundle().also {
                it.putString(
                    Constants.TEAM_NUMBER,
                    when (currentOrdering) {
                        Orders.FIRST -> firstOrder[position].team_number.toString()
                        Orders.SECOND -> secondOrder[position].team_number.toString()
                    }
                )
            }
            // Switch to the team details fragment.
            val ft = fragmentManager!!.beginTransaction()
            ft.setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
            ft.addToBackStack(null).replace((view!!.parent as ViewGroup).id, teamDetailsFragment)
                .commit()
        }

        return root
    }

    /**
     * Refreshes the list from the data in [`databaseReference`][StartupActivity.databaseReference].
     * Uses coroutines to generate the first rank sort and the second rank sort at the same time.
     * Should be called when there is new data pulled.
     */
    private fun refreshList() {
        // Create a CoroutineScope using runBlocking.
        runBlocking {
            // Launch both of the sorting jobs, running asynchronously.
            val firstJob = async {
                StartupActivity.databaseReference!!.picklist.sortedBy { it.first_rank }
            }
            val secondJob = async {
                StartupActivity.databaseReference!!.picklist.sortedBy { it.second_rank }
            }
            // Once both are done, save the generated lists.
            firstOrder = firstJob.await()
            secondOrder = secondJob.await()
            // Update the adapter.
            updateList()
        }
    }

    /**
     * Updates the text styles of the header buttons based on the current ordering. Called when the
     * order is changed.
     */
    private fun updateHeaders() {
        when (currentOrdering) {
            Orders.FIRST -> {
                root.btn_first_rank.typeface = Typeface.DEFAULT_BOLD
                root.btn_second_rank.typeface = Typeface.DEFAULT
            }
            Orders.SECOND -> {
                root.btn_first_rank.typeface = Typeface.DEFAULT
                root.btn_second_rank.typeface = Typeface.DEFAULT_BOLD
            }
        }
    }

    /**
     * Updates the adapter of the [ListView][android.widget.ListView]. Called when different data
     * needs to be displayed, for example when the user selects a different sort order.
     */
    private fun updateList() {
        adapter = LivePicklistAdapter(
            context = context!!,
            teams = when (currentOrdering) {
                Orders.FIRST -> firstOrder
                Orders.SECOND -> secondOrder
            },
            currentOrdering = currentOrdering,
            onNotifyDataSetChanged = { refreshList() }
        )
        root.lv_live_picklist.adapter = adapter
    }
}
