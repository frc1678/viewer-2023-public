package org.citruscircuits.viewer.fragments.alliance_details

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.fragment.app.FragmentActivity
import kotlinx.android.synthetic.main.alliance_details_cell.view.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonPrimitive
import org.citruscircuits.viewer.R
import org.citruscircuits.viewer.StartupActivity
import org.citruscircuits.viewer.constants.Constants
import org.citruscircuits.viewer.data.getPredictedAlliancesDataByKey


class AllianceDetailsAdapter(context: FragmentActivity) : BaseAdapter() {

    private val inflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount() = StartupActivity.databaseReference?.alliance?.size!!
    override fun getItem(position: Int) =
        StartupActivity.databaseReference?.alliance?.get((position + 1).toString())

    override fun getItemId(position: Int) = position.toLong()

    @SuppressLint("ViewHolder", "SetTextI18n")
    /**
     * Populates the predicted data for each elim alliance
     * Sets the view to ? if data is null
     */
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val rowView = inflater.inflate(R.layout.alliance_details_cell, parent, false)
        rowView.alliance_details_alliance_num.text = (position + 1).toString()

        // Gets the team number for each pick on an alliance
        val picks = Json.parseToJsonElement(getPredictedAlliancesDataByKey(
            position + 1, "picks"
        )!!.filter { it != '\'' }).jsonArray

        // Creates list of every team text in the given alliance
        var teamTextList = listOf(
            rowView.alliance_details_team1,
            rowView.alliance_details_team2,
            rowView.alliance_details_team3
        )

        // Sets the team number for each team text according to their pick order
        rowView.alliance_details_team1.text = picks[0].jsonPrimitive.content
        rowView.alliance_details_team2.text = picks[1].jsonPrimitive.content
        rowView.alliance_details_team3.text = picks[2].jsonPrimitive.content

        // Sets the text for all of the predicted data for the given alliance
        rowView.alliance_details_auto_score.text = "%.2f".format(
            getPredictedAlliancesDataByKey(position + 1, "predicted_auto_score")?.toFloatOrNull()
                ?: Constants.NULL_CHARACTER
        )
        rowView.alliance_details_tele_score.text = "%.2f".format(
            getPredictedAlliancesDataByKey(position + 1, "predicted_tele_score")?.toFloatOrNull()
                ?: Constants.NULL_CHARACTER
        )
        rowView.alliance_details_endgame_score.text = "%.2f".format(
            getPredictedAlliancesDataByKey(position + 1, "predicted_charge_score")?.toFloatOrNull()
                ?: Constants.NULL_CHARACTER
        )
        rowView.alliance_details_total_score.text = "%.2f".format(
            getPredictedAlliancesDataByKey(position + 1, "predicted_score")?.toFloatOrNull()
                ?: Constants.NULL_CHARACTER
        )

        // Strikes through an alliance when the user checks the given checkbox
        rowView.checkbox_strike.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked) {
                for (team in teamTextList) {
                    team.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                }
            } else {
                for (team in teamTextList) {
                    team.paintFlags = 0
                }
            }
        }

        return rowView
    }
}
