package com.example.viewer_2022.fragments.offline_picklist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.viewer_2022.MainViewerActivity
import com.example.viewer_2022.R
import com.example.viewer_2022.constants.Constants
import com.example.viewer_2022.databinding.LivePicklistCellBinding
import com.example.viewer_2022.fragments.team_details.TeamDetailsFragment

/**
 * Recycler adapter for live picklist
 */
class OfflinePicklistAdapter(val context: OfflinePicklistFragment) :
    ListAdapter<String, OfflinePicklistAdapter.OfflinePicklistViewHolder>(object :
        DiffUtil.ItemCallback<String>() {

        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem.contentEquals(newItem)
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem.contentEquals(newItem)
        }


    }) {
    inner class OfflinePicklistViewHolder(private val itemViewBinding: LivePicklistCellBinding) :
        RecyclerView.ViewHolder(itemViewBinding.root) {
        fun bindRoot(teamNumber: String) {
            if (context.picklistData.ranking.contains(teamNumber)) {
                itemViewBinding.tvFirstRank.text =
                    context.picklistData.ranking.indexOf(teamNumber).plus(1).toString()
                itemViewBinding.root.setBackgroundColor(context.resources.getColor(R.color.White))
            } else {
                itemViewBinding.root.setBackgroundColor(context.resources.getColor(R.color.Red))
                itemViewBinding.tvFirstRank.text = "-"
            }
            itemViewBinding.tvTeamNumber.text = teamNumber
            itemViewBinding.root.setOnClickListener { onClick(teamNumber) }

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfflinePicklistViewHolder {
        return OfflinePicklistViewHolder(

            LivePicklistCellBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )

        )
    }

    override fun onBindViewHolder(holder: OfflinePicklistViewHolder, position: Int) {

        val picklistItem = this.getItem(position)
        holder.bindRoot(picklistItem)
    }


    fun onClick(teamNumber: String) {
        if (teamNumber in MainViewerActivity.teamList) {
            val teamDetailsFragment = TeamDetailsFragment()

            // Put the arguments for the team details fragment.
            teamDetailsFragment.arguments = Bundle().also {
                it.putString(
                    Constants.TEAM_NUMBER,
                    teamNumber
                )
            }
            // Switch to the team details fragment.
            val ft = context.fragmentManager!!.beginTransaction()
            ft.setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
            context.view?.rootView?.findViewById<ViewGroup>(R.id.nav_host_fragment)?.let {
                ft.addToBackStack(null).replace(it.id, teamDetailsFragment)
                    .commit()
            }
        }
    }

    fun handleOrderChange(from: Int, to: Int) {
        val newRanking = context.picklistData.ranking.toMutableList()
        newRanking.add(to, newRanking.removeAt(from))

        context.saveData(newRanking, context.picklistData.dnp)
        context.updateData()

    }

    fun handleDNPToggle(position: Int) {
        val teamNumber = this.getItem(position)
        val newRanking = context.picklistData.ranking.toMutableList()
        val newDnp = context.picklistData.dnp.toMutableList()
        if (newDnp.contains(teamNumber)) {
            newDnp.remove(teamNumber)
            newRanking.add(teamNumber)
        } else {
            newDnp.add(teamNumber)
            newRanking.remove(teamNumber)
        }
        context.saveData(newRanking, newDnp)
        context.updateData()
    }


}
