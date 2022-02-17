package com.example.viewer_2020.fragments.team_ranking

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.fragment.app.FragmentActivity
import com.example.viewer_2020.R
import com.example.viewer_2020.TeamRankingItem
import com.example.viewer_2020.constants.Constants
import kotlinx.android.synthetic.main.team_ranking_cell.view.*

class TeamRankingListAdapter(private val context: FragmentActivity, private val teamNumber: String?, private var items: List<TeamRankingItem>) : BaseAdapter() {
    private val inflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(position: Int): TeamRankingItem {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val e = getItem(position)
        val rowView = inflater.inflate(R.layout.team_ranking_cell, parent, false)

        rowView.tv_team_number_ranking.text = e.teamNumber
        rowView.tv_value_ranking.text = e.value
        rowView.tv_team_ranking.text = if (e.value == Constants.NULL_CHARACTER) Constants.NULL_PREDICTED_SCORE_CHARACTER else (position + 1).toString()

        if(e.teamNumber == teamNumber){
            rowView.setBackgroundColor(context.resources.getColor(R.color.ElectricGreen))
        }


        return rowView
    }

    fun updateItems(newItems: List<TeamRankingItem>){
        items = newItems
        this.notifyDataSetChanged()
    }
}

fun floatToString(float: Float): String {
    var str = float.toString()

    if(str.endsWith(".0")){
        str = str.dropLast(2)
    }
    return str
}