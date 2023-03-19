package org.citruscircuits.viewer.fragments.alliance_details

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.fragment.app.FragmentActivity
import kotlinx.android.synthetic.main.alliance_details_cell.view.alliance_details_alliance_num
import kotlinx.android.synthetic.main.alliance_details_cell.view.alliance_details_auto_score
import kotlinx.android.synthetic.main.alliance_details_cell.view.alliance_details_endgame_score
import kotlinx.android.synthetic.main.alliance_details_cell.view.alliance_details_team1
import kotlinx.android.synthetic.main.alliance_details_cell.view.alliance_details_team2
import kotlinx.android.synthetic.main.alliance_details_cell.view.alliance_details_team3
import kotlinx.android.synthetic.main.alliance_details_cell.view.alliance_details_tele_score
import kotlinx.android.synthetic.main.alliance_details_cell.view.alliance_details_total_score
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
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val rowView = inflater.inflate(R.layout.alliance_details_cell, parent, false)
        rowView.alliance_details_alliance_num.text = (position + 1).toString()
        val picks = Json.parseToJsonElement(getPredictedAlliancesDataByKey(
            position + 1, "picks"
        )!!.filter { it != '\'' }).jsonArray
        rowView.alliance_details_team1.text = picks[0].jsonPrimitive.content
        rowView.alliance_details_team2.text = picks[1].jsonPrimitive.content
        rowView.alliance_details_team3.text = picks[2].jsonPrimitive.content
        rowView.alliance_details_auto_score.text =
            getPredictedAlliancesDataByKey(position + 1, "predicted_auto_score")
                ?: Constants.NULL_CHARACTER
        rowView.alliance_details_tele_score.text =
            getPredictedAlliancesDataByKey(position + 1, "predicted_tele_score")
                ?: Constants.NULL_CHARACTER
        rowView.alliance_details_endgame_score.text =
            getPredictedAlliancesDataByKey(position + 1, "predicted_charge_score")
                ?: Constants.NULL_CHARACTER
        rowView.alliance_details_total_score.text =
            getPredictedAlliancesDataByKey(position + 1, "predicted_score")
                ?: Constants.NULL_CHARACTER
        return rowView
    }
}
