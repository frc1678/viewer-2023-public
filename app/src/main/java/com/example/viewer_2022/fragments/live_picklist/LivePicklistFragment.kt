package com.example.viewer_2022.fragments.live_picklist

import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.viewer_2022.MainViewerActivity
import com.example.viewer_2022.R
import com.example.viewer_2022.RefreshManager
import com.example.viewer_2022.StartupActivity
import com.example.viewer_2022.constants.Constants
import com.example.viewer_2022.data.DatabaseReference
import com.example.viewer_2022.fragments.team_details.TeamDetailsFragment
import kotlinx.android.synthetic.main.fragment_live_picklist.view.*
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

/**
 * The fragment for the live team orderings from the picklist.
 *
 * @see R.layout.fragment_live_picklist
 */
class LivePicklistFragment : Fragment() {
    private val teamDetailsFragment = TeamDetailsFragment()

    private lateinit var root: View

    /**
     * The refresh ID used for live resync.
     *
     * @see RefreshManager
     */
    private var refreshId: String? = null

    /** The list of teams with their picklist rankings, sorted by their first rank. */
    lateinit var firstOrder: MutableList<DatabaseReference.PicklistTeam>

    /** The list of teams with their picklist rankings, sorted by their second rank. */
    lateinit var secondOrder: MutableList<DatabaseReference.PicklistTeam>

    /**
     * The possible orderings of the picklist.
     *
     * @see currentOrdering
     */
    enum class Orders { FIRST, SECOND }

    /**
     * The ordering that is currently being used. Has a custom setter method to automatically notify
     * the adapter that the data has changed.
     */
    var currentOrdering = Orders.FIRST
        set(value) {
            field = value
            updateHeaders()
            // Notify the adapter that the data needs to be reloaded.
            if (root.lv_live_picklist.adapter != null) {
                (root.lv_live_picklist.adapter as LivePicklistAdapter).notifyDataSetChanged()
            }
        }

    /** Runs when the fragment is created, to inflate the layout. */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the picklist layout.
        root = inflater.inflate(R.layout.fragment_live_picklist, container, false)

        // Generate and show the list.
        refreshLists()
        initAdapter()
        updateHeaders()

        // Register the refresh listener for live resync.
        if (refreshId == null) {
            refreshId = MainViewerActivity.refreshManager.addRefreshListener {
                refreshLists()
                Log.d("data-refresh", "Updated: Live Picklist")
            }
        }

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
     * Removes the live resync listener when the fragment is destroyed.
     */
    override fun onDestroy() {
        super.onDestroy()
        MainViewerActivity.refreshManager.removeRefreshListener(refreshId)
    }

    /**
     * Refreshes the list from the data in [`databaseReference`][StartupActivity.databaseReference].
     * Uses coroutines to generate the first rank sort and the second rank sort at the same time.
     * Should be called when there is new data pulled.
     */
    private fun refreshLists() {
        if (StartupActivity.databaseReference!!.picklist.isEmpty()) {
            // If the picklist is empty for some reason, the orders should be empty as well.
            firstOrder = mutableListOf()
            secondOrder = mutableListOf()
        } else {
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
                firstOrder = firstJob.await() as MutableList<DatabaseReference.PicklistTeam>
                secondOrder = secondJob.await() as MutableList<DatabaseReference.PicklistTeam>
            }
        }
        // Notify the adapter that the data has been updated.
        if (root.lv_live_picklist.adapter != null) {
            (root.lv_live_picklist.adapter as LivePicklistAdapter).notifyDataSetChanged()
        }
    }

    /**
     * Initializes the adapter of the [ListView][android.widget.ListView].
     */
    private fun initAdapter() {
        root.lv_live_picklist.adapter = LivePicklistAdapter(
            context = context!!,
            fragment = this
        )
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
}
