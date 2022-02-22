package com.example.viewer_2022.fragments.live_picklist

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.viewer_2022.R
import com.example.viewer_2022.data.DatabaseReference
import kotlinx.android.synthetic.main.live_picklist_cell.view.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * The adapter for the [`ListView`][android.widget.ListView] in the
 * [Live Picklist fragment][LivePicklistFragment].
 *
 * @param teams The list of teams to be displayed, in the form of
 * [`PicklistTeam`][DatabaseReference.PicklistTeam] objects. This list should already be sorted.
 * @param currentOrdering The current ordering of the list of teams.
 */
class LivePicklistAdapter(
    context: Context,
    private val teams: List<DatabaseReference.PicklistTeam>,
    private val currentOrdering: LivePicklistFragment.Orders
) : BaseAdapter() {

    /** A convenience object for inflating layouts. */
    private val inflater = LayoutInflater.from(context)

    /** The number of teams displayed in the list. */
    override fun getCount() = teams.size

    /** The number of the team at the given position in the list. */
    override fun getItem(i: Int) = teams[i].team_number

    /** The ID of the row at the given position in the list. Simply uses the position. */
    override fun getItemId(position: Int) = position.toLong()

    /** Gets the view in the specified row of the list. */
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val row: View?
        if (convertView == null) { // The row is being inflated for the first time.
            // Inflate the row's layout.
            row = inflater.inflate(R.layout.live_picklist_cell, parent, false)
            // Set the tag of the row so that it doesn't need to be reinflated if it gets loaded
            // again.
            row.tag = TeamHolder(row.tv_team_number, row.tv_first_rank, row.tv_second_rank)
        } else { // The row was already inflated, and doesn't need to be inflated again.
            row = convertView
        }
        // Populate the row with the data.
        val team = teams[position]
        runBlocking { // Asynchronous jobs to make populating much faster.
            launch { row?.tv_team_number?.text = team.team_number.toString() }
            launch { row?.tv_first_rank?.text = team.first_rank.toString() }
            launch { row?.tv_second_rank?.text = team.second_rank.toString() }
            // Set the text style based on the current ordering.
            launch {
                row?.tv_first_rank?.typeface = when (currentOrdering) {
                    LivePicklistFragment.Orders.FIRST -> Typeface.DEFAULT_BOLD
                    LivePicklistFragment.Orders.SECOND -> Typeface.DEFAULT
                }
            }
            launch {
                row?.tv_second_rank?.typeface = when (currentOrdering) {
                    LivePicklistFragment.Orders.FIRST -> Typeface.DEFAULT
                    LivePicklistFragment.Orders.SECOND -> Typeface.DEFAULT_BOLD
                }
            }
        }
        return row!!
    }

    /**
     * A class to hold the views in each row of the list, so that they don't have to be reinflated
     * every time they are loaded.
     */
    data class TeamHolder(
        val teamNumberView: TextView,
        val firstRankView: TextView,
        val secondRankView: TextView
    )
}
