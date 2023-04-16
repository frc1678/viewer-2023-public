package org.citruscircuits.viewer.fragments.alliance_details

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import kotlinx.android.synthetic.main.alliance_details_cell.view.*
import kotlinx.android.synthetic.main.match_details_cell.view.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonPrimitive
import org.citruscircuits.viewer.MainViewerActivity
import org.citruscircuits.viewer.R
import org.citruscircuits.viewer.StartupActivity
import org.citruscircuits.viewer.constants.Constants
import org.citruscircuits.viewer.data.getPredictedAlliancesDataByKey
import java.io.File


class AllianceDetailsAdapter(
    private val context: FragmentActivity
) : BaseAdapter() {

    private val inflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount() = StartupActivity.databaseReference?.alliance?.size!!
    override fun getItem(position: Int) =
        StartupActivity.databaseReference?.alliance?.get(((position % 8) + 1).toString())

    override fun getItemId(position: Int) = position.toLong()

    @SuppressLint("ViewHolder", "SetTextI18n")
    /**
     * Populates the predicted data for each elim alliance
     * Sets the view to ? if data is null
     */
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val rowView = inflater.inflate(R.layout.alliance_details_cell, parent, false)
        val allianceNumber = (position + 1).toString()

        rowView.alliance_details_alliance_num.text = (((position % 8) + 1).toString() +
                if(position in 0..7){
                    " 3rd"
                } else{
                    " 4th"
                })

        // Gets the team number for each pick on an alliance
        val picks = Json.parseToJsonElement(getPredictedAlliancesDataByKey(
            allianceNumber.toInt(), "picks"
        )!!.filter { it != '\'' }).jsonArray

        val file = File(Constants.DOWNLOADS_FOLDER, "viewer_elim_alliances.json")

        // Creates list of every team text in the given alliance
        val teamTextList = listOf(
            rowView.alliance_details_alliance_num,
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
            getPredictedAlliancesDataByKey(allianceNumber.toInt(), "predicted_auto_score")?.toFloatOrNull()
                ?: Constants.NULL_CHARACTER
        )
        rowView.alliance_details_tele_score.text = "%.2f".format(
            getPredictedAlliancesDataByKey(allianceNumber.toInt(), "predicted_tele_score")?.toFloatOrNull()
                ?: Constants.NULL_CHARACTER
        )
        rowView.alliance_details_endgame_score.text = "%.2f".format(
            getPredictedAlliancesDataByKey(allianceNumber.toInt(), "predicted_charge_score")?.toFloatOrNull()
                ?: Constants.NULL_CHARACTER
        )
        rowView.alliance_details_total_score.text = "%.2f".format(
            getPredictedAlliancesDataByKey(allianceNumber.toInt(), "predicted_score")?.toFloatOrNull()
                ?: Constants.NULL_CHARACTER
        )

        if(MainViewerActivity.eliminatedAlliances.contains(allianceNumber)) {
            for (team in teamTextList) {
                team.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            }
            rowView.playoff_alliances.setBackgroundColor(
                ContextCompat.getColor(context, R.color.MediumGray)
            )
            rowView.checkbox_strike.isChecked = true
        } else {
            for (team in teamTextList) {
                team.paintFlags = 0
            }
            rowView.playoff_alliances.setBackgroundColor(
                ContextCompat.getColor(context, R.color.White)
            )
            rowView.checkbox_strike.isChecked = false
        }

        // Strikes through an alliance when the user checks the given checkbox
        rowView.checkbox_strike.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked) {
                MainViewerActivity.eliminatedAlliances.add(allianceNumber)
            } else {
                MainViewerActivity.eliminatedAlliances.remove(allianceNumber)
            }
            MainViewerActivity.EliminatedAlliances.input()

            if(MainViewerActivity.eliminatedAlliances.contains(allianceNumber)) {
                for (team in teamTextList) {
                    team.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                }
                rowView.playoff_alliances.setBackgroundColor(
                    ContextCompat.getColor(context, R.color.MediumGray)
                )
                rowView.checkbox_strike.isChecked = true
            } else {
                for (team in teamTextList) {
                    team.paintFlags = 0
                }
                rowView.playoff_alliances.setBackgroundColor(
                    ContextCompat.getColor(context, R.color.White)
                )
                rowView.checkbox_strike.isChecked = false
            }
        }

        return rowView
    }
}
